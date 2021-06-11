package com.ts.flow.template;

import com.ts.flow.context.DataContext;
import com.ts.flow.enums.ResultEnum;
import com.ts.flow.part.BasePart;

/**
 * @Title: TestA.java
 * @Description: 测试工具A
 * @author wh
 * @date 2021年6月8日  
 * @version V1.0
 */
public class TestA extends BasePart {

	@Override
	public ResultEnum doService(DataContext context) {
		System.out.println("测试A执行");
		context.put("A", "测试A执行");
		return ResultEnum.SUCCESS;
	}

}

