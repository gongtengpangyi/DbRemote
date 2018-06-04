package com.frz.frame.ssm;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ToolSpring implements ApplicationContextAware {
	
	private static ApplicationContext applicationContext = null;

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		if(ToolSpring.applicationContext == null) {
			ToolSpring.applicationContext = applicationContext;
			System.out.println();
			System.out.println();
			System.out.println("---------------------------------------------------------------------");
			System.out.println("========ApplicationContext配置成功,在普通类可以通过调用ToolSpring.getAppContext()获取applicationContext对象,applicationContext="
							+ applicationContext + "========");
			System.out.println("---------------------------------------------------------------------");
			System.out.println();
			System.out.println();
			
		}
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}
	
}
