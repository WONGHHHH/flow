package com.ts.flow.template;

import com.ts.flow.context.DataContext;
import com.ts.flow.enums.ResultEnum;
import com.ts.flow.part.BasePart;

/**
 * @Title: TestB.java
 * @Description: 测试工具B
 * @author wh
 * @date 2021年6月8日  
 * @version V1.0
 */
public class TestB extends BasePart {

	@Override
	public ResultEnum doService(DataContext context) {
		System.out.println("测试B执行");
		context.put("B", "测试B执行");
		return ResultEnum.SUCCESS;
	}

}

