package com.ts.flow.node;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;

import com.ts.flow.document.FlowResolver;
import com.ts.flow.exception.ServiceDomException;


/**
 * @Title: NodeHelper.java
 * @Description: 辅助构造node
 * @author wh
 * @date 2021年6月3日  
 * @version V1.0
 */
public class NodeHelper {
	
	public static Node buildNode(Element element) throws ServiceDomException {
		Node node = new Node();
		String name = element.getName();
		if("ref".equals(name)) {
			String refpartValue = element.attributeValue("refpart");
			Element refPart = FlowResolver.getRefPart(refpartValue);
			if(refPart == null) {
				throw new ServiceDomException("当前引用的flow-common没有找到" + refPart);
			}
			element = refPart;
		}
		String partId = element.attributeValue("id");
		String partClass = element.attributeValue("class");
		if(StringUtils.isEmpty(partId)) {
			throw new ServiceDomException("part的属性id不允许为空");
		}
		if(StringUtils.isEmpty(partClass)) {
			throw new ServiceDomException(partId + "part的属性class不允许为空");
		}
		String partDesc = element.attributeValue("desc");
		node.setId(partId);
		node.setRefClass(partClass);
		node.setDesc(partDesc);
		return node;
	}
	
}

