package com.nusyn.license.manager;

import org.springframework.context.ApplicationContext;

public class SpringContextManager {

	private static SpringContextManager instance;
	
	private  ApplicationContext CONTEXT;
	
	private SpringContextManager(){
		
	}
	
	
	public static SpringContextManager getInstance(){
		if(instance== null){
			instance = new SpringContextManager();
		}
		return instance;
	}
	
	public void setApplicationContext(ApplicationContext context){
		this.CONTEXT = context;
	}
	
	public Object getSpringBean(String beanName){
		return CONTEXT.getBean(beanName);
	}
}
