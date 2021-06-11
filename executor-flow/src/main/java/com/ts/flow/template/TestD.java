package com.ts.flow.template;

import com.ts.flow.context.DataContext;
import com.ts.flow.enums.ResultEnum;
import com.ts.flow.part.BasePart;

/**
 * @Title: TestD.java
 * @Description: 测试工具D
 * @author wh
 * @date 2021年6月8日  
 * @version V1.0
 */
public class TestD extends BasePart {

	@Override
	public ResultEnum doService(DataContext context) {
		System.out.println("测试D执行");
		context.put("D", "测试D执行");
		return ResultEnum.SUCCESS;
	}

}

