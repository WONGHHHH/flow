package com.ts.flow.template;

import com.ts.flow.context.DataContext;
import com.ts.flow.enums.ResultEnum;
import com.ts.flow.part.BasePart;

/**
 * @Title: TestF.java
 * @Description: 测试工具F
 * @author wh
 * @date 2021年6月8日  
 * @version V1.0
 */
public class TestF extends BasePart {

	@Override
	public ResultEnum doService(DataContext context) {
		System.out.println("测试F执行");
		context.put("F", "测试F执行");
		return ResultEnum.SUCCESS;
	}

}

