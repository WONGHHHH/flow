package com.ts.flow.spring;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @Title: ApplicationContextHelper.java
 * @Description: Spring容器辅助器，注册bean
 * @author wh
 * @date 2021年6月2日  
 * @version V1.0
 */
public class ApplicationContextHelper {
	
	public static void registerBean(ConfigurableApplicationContext applicationContext, String className) {
		try {
			Class<?> bean = Class.forName(className);
			DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getBeanFactory();
			BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(bean);
			beanFactory.registerBeanDefinition(className, beanDefinitionBuilder.getBeanDefinition());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}

