package com.ts.flow.node.flow;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;

import com.ts.flow.exception.ServiceDomException;

/**
 * @Title: FlowHelper.java
 * @Description: 辅助创建flow
 * @author wh
 * @date 2021年6月3日  
 * @version V1.0
 */
public class FlowHelper {
	
	public static Flow buildFlow(Element element) throws ServiceDomException {
		String flowId = element.attributeValue("id");
		if(StringUtils.isEmpty(flowId)) {
			throw new ServiceDomException("<flow>节点的id属性不能为空");
		}
		Flow flow = new Flow();
		flow.setId(flowId);
		flow.setName(element.attributeValue("name"));
		flow.setDesc(element.attributeValue("desc"));
		return flow;
	}
	
}

