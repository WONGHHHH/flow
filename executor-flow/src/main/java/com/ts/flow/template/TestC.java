package com.ts.flow.template;

import com.ts.flow.context.DataContext;
import com.ts.flow.enums.ResultEnum;
import com.ts.flow.part.BasePart;

/**
 * @Title: TestC.java
 * @Description: 测试工具C
 * @author wh
 * @date 2021年6月8日  
 * @version V1.0
 */
public class TestC extends BasePart {

	@Override
	public ResultEnum doService(DataContext context) {
		System.out.println("测试C执行");
		context.put("C", "测试C执行");
		return ResultEnum.SUCCESS;
	}

}

