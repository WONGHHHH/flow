package com.ts.flow.service;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;

import com.ts.flow.exception.ServiceDomException;

/**
 * @Title: ServiceHelper.java
 * @Description: 辅助创建service
 * @author wh
 * @date 2021年6月3日  
 * @version V1.0
 */
public class ServiceHelper {
	
	public static Service buildService(Element element) throws ServiceDomException {
		String serviceId = element.attributeValue("id");
		String serviceSource = element.attributeValue("source");
		if(StringUtils.isEmpty(serviceId)) {
			throw new ServiceDomException("当前节点"+element.getName()+"id不允许为空");
		}
		if(StringUtils.isEmpty(serviceSource)) {
			throw new ServiceDomException("当前节点"+element.getName()+"source不允许为空");
		}
		Service service = new Service();
		service.setId(serviceId);
		service.setSource(serviceSource);
		service.setName(element.attributeValue("name"));
		service.setDesc(element.attributeValue("desc"));
		return service;
	}
	
}









