package com.ts.flow.part;

import org.springframework.stereotype.Component;

import com.ts.flow.context.DataContext;
import com.ts.flow.enums.ResultEnum;

/**
 * @Title: StartPart.java
 * @Description: 起始节点，不做任何处理
 * @author wh
 * @date 2021年6月3日  
 * @version V1.0
 */
@Component
public class StartPart extends BasePart {

	@Override
	public ResultEnum doService(DataContext context) {
		return ResultEnum.SUCCESS;
	}

}

