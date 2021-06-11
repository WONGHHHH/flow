package com.ts.flow.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;

import com.ts.flow.context.DataContext;
import com.ts.flow.document.FlowResolver;
import com.ts.flow.enums.ResponseEnum;
import com.ts.flow.exception.ServiceDomException;
import com.ts.flow.node.Node;
import com.ts.flow.node.flow.Flow;
import com.ts.flow.node.flow.FlowHelper;

/**
 * @Title: ServiceLaucher.java
 * @Description: 服务执行器
 * @author wh
 * @date 2021年6月3日  
 * @version V1.0
 */
public class ServiceLauncher extends ApplicationObjectSupport{
	
	private Map<String, Service> services;
	
	private String resource;
	
	public Map<String, Service> getServices() {
		return services;
	}


	public String getResource() {
		return resource;
	}


	public void setResource(String resource) {
		this.resource = resource;
	}
	
	/** 
	* @methodName  : init
	* @param       : 
	* @return      : void 
	* @Description : 初始化解析XML注册bean
	*/
	public void init() {
		services = new HashMap<String, Service>();
		ConfigurableApplicationContext  applicationContext = (ConfigurableApplicationContext) getApplicationContext();
		try {
			FlowResolver.initApplicationContext(applicationContext);
			FlowResolver.init(resource);
			List<Element> serviceElements = FlowResolver.serviceElement();
			for(Element serviceElement : serviceElements) {
				String serviceId = serviceElement.attributeValue("id");
				if(services.containsKey(serviceId)) {
					throw new ServiceDomException("serviced的id属性"+serviceId+"必须唯一");
				}
				Service service = ServiceHelper.buildService(serviceElement);
				
				
				List<Element> serviceFlows = FlowResolver.serviceFlow(serviceElement);
				if(serviceFlows == null || serviceFlows.isEmpty()) {
					//当前service节点没有配置执行流程
					continue;
				}
				
				//构造flow
				Map<String, Flow> flows = new HashMap<String, Flow>();
				for(Element serviceFlow : serviceFlows) {
					if(flows.containsKey(serviceFlow.attributeValue(serviceId))) {
						throw new ServiceDomException("当前service"+serviceId+"节点下存在相同的流程");
					}
					Flow flow = FlowHelper.buildFlow(serviceFlow);
					
					//构造调用链
					Node node = FlowResolver.buildLinkedNode(serviceFlow);
					flow.setStartNode(node);
					flows.put(flow.getId(), flow);
				}
				service.setFlows(flows);
        		services.put(service.getId(),service);
			}
			
			
		} catch (ServiceDomException e) {
			e.printStackTrace();
			//初始化异常直接退出
			System.exit(0);
		}
		
	}
	
	public DataContext execute(String serviceId, String flowId, DataContext dataContext) {
		if(StringUtils.isEmpty(serviceId)) {
			dataContext.buildParam(ResponseEnum.FAIL);
			return dataContext;
		}
		if(StringUtils.isEmpty(flowId)) {
			dataContext.buildParam(ResponseEnum.FAIL);
			return dataContext;
		}
		Service service = services.get(serviceId);
		if(service == null) {
			dataContext.buildParam(ResponseEnum.FAIL);
			return dataContext;
		}
		Flow flow = service.getFlows().get(flowId);
		if(flow == null) {
			dataContext.buildParam(ResponseEnum.FAIL);
			return dataContext;
		}
		Node startNode = flow.getStartNode();
		try {
			startNode.doNode(dataContext);
		} catch (Exception e) {
			dataContext.buildParam(ResponseEnum.FAIL);
			return dataContext;
		}
		return dataContext;
	}
}















