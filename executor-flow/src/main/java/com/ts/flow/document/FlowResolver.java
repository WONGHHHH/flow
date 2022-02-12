package com.ts.flow.document;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.context.ConfigurableApplicationContext;

import com.ts.flow.exception.ServiceDomException;
import com.ts.flow.node.Node;
import com.ts.flow.node.NodeHelper;
import com.ts.flow.node.flow.Flow;
import com.ts.flow.node.flow.FlowHelper;
import com.ts.flow.spring.ApplicationContextHelper;

/**
 * @Title: FlowResolver.java
 * @Description: 解析XML文件
 * @author wh
 * @date 2021年6月2日  
 * @version V1.0
 */
public class FlowResolver {
	
	private static Map<String, Element> COMMON_PARTS;
	
	private static Map<String, Element> COMMON_FLOWS;
	
	private static ConfigurableApplicationContext applicationContext;
	
	private static Element ROOT;
	
	@SuppressWarnings("rawtypes")
	public static void init(String resource) throws ServiceDomException {
		initStartPart();
		String path = "";
		if(StringUtils.isEmpty(resource)) {
			//没有配置路径走默认的路径
			path = FlowResolver.class.getResource("/").getPath() + "flow";
		}else {
			path = resource;
		}
		if(!new File(path).exists()) {
			throw new ServiceDomException(path + "流程配置文件没有找到");
		}
		
		ROOT = getRoot(path);
		COMMON_PARTS = new HashMap<String, Element>();
		for(Iterator partCommonIterator = ROOT.elementIterator("part-common");partCommonIterator.hasNext();) {
			Element element = (Element) partCommonIterator.next();
			String partId = element.attributeValue("id");
			if(StringUtils.isEmpty(partId)) {
				throw new ServiceDomException("part-common的id属性不能为空");
			}
			if(COMMON_PARTS.containsKey(partId)) {
				throw new ServiceDomException("part-common的id属性["+partId+"]必须唯一");
			}
			COMMON_PARTS.put(partId, element);
		}
		COMMON_FLOWS = new HashMap<String, Element>();
		for(Iterator flowCommonIterator = ROOT.elementIterator("flow-common");flowCommonIterator.hasNext();) {
			Element element = (Element)flowCommonIterator.next();
			String flowId = element.attributeValue("id");
			if(StringUtils.isEmpty(flowId)) {
				throw new ServiceDomException("flow-common的id属性不能为空");
			}
			if(COMMON_FLOWS.containsKey(flowId)) {
				throw new ServiceDomException("flow-common的id属性["+flowId+"]必须唯一");
			}
			COMMON_FLOWS.put(flowId, element);
		}
	}

	public static void initApplicationContext(ConfigurableApplicationContext context) {
		applicationContext = context;
	}

	private static void initStartPart(){
		ApplicationContextHelper.registerBean(applicationContext, "com.ts.flow.part.StartPart");
	}

	/** 
	* @methodName  : getRoot
	* @param       : @param path
	* @param       : @return
	* @return      : Element 
	* @throws Exception 
	* @Description : 获取所有flow的XML文件，获取根节点
	*/
	@SuppressWarnings("unchecked")
	private static Element getRoot(String path) throws ServiceDomException {
		File dir = new File(path);
		File[] flowFiles = dir.listFiles();
		
		Element root = null;
		
		//将所有文件合并
		for(File file : flowFiles) {
			if(!file.getName().endsWith(".xml")) {
				//只解析XML文件
				continue;
			}
			Document document = getDocument(file);
			Element element = document.getRootElement();
			if(root == null) {
				root = element;
				continue;
			}
			List<Element> elements = element.elements();
			for (Element e : elements) {  
				root.add(e.detach());//将b下的节点添加到a的根节点下  
	        } 
			
		}
		
		return root;
	}

	/** 
	* @methodName  : getDocument
	* @param       : @param file
	* @param       : @return
	* @return      : Document 
	* @throws ServiceDomException 
	* @throws DocumentException 
	* @Description : 解析文件
	*/
	private static Document getDocument(File file) throws ServiceDomException {
		SAXReader reader = new SAXReader();
		Document read = null;
		try {
			read = reader.read(file);
		} catch (DocumentException e) {
			throw new ServiceDomException("XML文件解析异常", e);
		}
		return read;
	}
	
	/** 
	* @methodName  : serviceElement
	* @param       : @return
	* @param       : @throws ServiceDomException
	* @return      : List<Element> 
	* @Description : 获取所有service节点
	*/
	@SuppressWarnings({"unchecked" })
	public static List<Element> serviceElement() throws ServiceDomException{
		List<Element> services = ROOT.selectNodes("service");
		if(services == null || services.isEmpty()) {
			throw new ServiceDomException("配置文件没有获取到<service></service>节点");
		}
		return services;
	}
	
	/** 
	* @methodName  : serviceFlow
	* @param       : @param element
	* @param       : @return
	* @return      : List<Element> 
	* @Description : 获取当前service节点下的flow，允许service下不配置flow
	*/
	@SuppressWarnings("unchecked")
	public static List<Element> serviceFlow(Element serviceElement){
		List<Element> flows = serviceElement.selectNodes("flow");
		return flows;
	}

	/** 
	* @methodName  : buildLinkedNode
	* @param       : @param serviceFlow
	* @param       : @return
	* @return      : Node 
	* @throws ServiceDomException 
	* @Description : 构造当前flow下的调用链
	*/
	public static Node buildLinkedNode(Element serviceFlow) throws ServiceDomException {
		//获取当前节点下的所有节点
		List<Element> elements = getSubElement(serviceFlow);
		//解析当前所有节点
		Node topNode = buildTopNode();
		for(Element element : elements) {
			if("subFlow".equals(element.getName())) {
				//当前节点为子流程
				Flow subFlow = loadSubFlow(element);
				Node node = new Node();
				node.setType("FLOW");
				node.setRefFlow(subFlow);
				setNextNode(topNode, node);
			}else {
				Node node = NodeHelper.buildNode(element);
				setNextNode(topNode, node);
				//生成bean
				ApplicationContextHelper.registerBean(applicationContext, node.getRefClass());
			}
		}
		
		return topNode;
	}

	/** 
	* @methodName  : buildTopNode
	* @param       : @return
	* @return      : Node 
	* @Description : 创建初始节点
	*/
	private static Node buildTopNode() {
		Node topNode = new Node();
		topNode.setId("startPart");
		topNode.setDesc("startNode");
		topNode.setRefClass("com.ts.flow.part.StartPart");
		return topNode;
	}

	/** 
	* @methodName  : loadSubFlow
	* @param       : @param element
	* @param       : @return
	* @return      : Flow 
	* @throws ServiceDomException 
	* @Description : 解析子流程
	*/
	private static Flow loadSubFlow(Element element) throws ServiceDomException {
		Node topNode = buildTopNode();
		String subFlowRef = element.attributeValue("ref");
		if(StringUtils.isEmpty(subFlowRef)) {
			throw new ServiceDomException(element.getName() + "当前节点必须有ref属性，必须引用通用流程flow-common");
		}
		Element flowElement = COMMON_FLOWS.get(subFlowRef);
		if(flowElement == null) {
			throw new ServiceDomException("当前引用的flow-common没有找到" + subFlowRef);
		}
		Flow flow = FlowHelper.buildFlow(flowElement);
		List<Element> subElements =  getSubElement(flowElement);
		for(Element subElement : subElements) {
			if("subFlow".equals(subElement.getName())) {
				//当前节点为子流程
				Flow subFlow = loadSubFlow(element);
				Node node = new Node();
				node.setType("FLOW");
				node.setRefFlow(subFlow);
				setNextNode(topNode, node);
			}else {
				Node node = NodeHelper.buildNode(subElement);
				setNextNode(topNode, node);
				//生成bean
				ApplicationContextHelper.registerBean(applicationContext, node.getRefClass());
			}
		}
		flow.setStartNode(topNode);
		return flow;
	}

	/** 
	* @methodName  : setNextNode
	* @param       : @param node
	* @param       : @param node2
	* @return      : void 
	* @Description : 插入下节点
	*/
	private static void setNextNode(Node topNode, Node node) {
		Node temp = topNode;
		while(temp.getNextNode()!=null) {
			temp = temp.getNextNode();
		}
		temp.setNextNode(node);
	}

	/** 
	* @methodName  : getSubElement
	* @param       : @param flowElement
	* @param       : @return
	* @return      : List<Element> 
	* @throws ServiceDomException 
	* @Description : 获取当前节点下的子节点
	*/
	@SuppressWarnings("unchecked")
	private static List<Element> getSubElement(Element flowElement) throws ServiceDomException {
		List<Element> elements = flowElement.elements();
		if(elements == null || elements.isEmpty()) {
			throw new ServiceDomException("当前节点没有配置执行链" + flowElement.attributeValue("id"));
		}
		return elements;
	}

	/** 
	* @methodName  : getRefPart
	* @param       : @param refpart
	* @return      : void 
	* @Description : 获取refpart
	*/
	public static Element getRefPart(String refPart) {
		Element partElement = COMMON_PARTS.get(refPart);
		return partElement;
	}
	
	public static ConfigurableApplicationContext getApplicationContext() {
		return applicationContext;
	}
}































