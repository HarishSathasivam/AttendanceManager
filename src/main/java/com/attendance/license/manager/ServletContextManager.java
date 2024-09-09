package com.nusyn.license.manager;

import javax.servlet.ServletContext;

public class ServletContextManager {

	private static ServletContextManager instance;
	
	private ServletContext servletContext;
	
	private ServletContextManager(){
		
	}
	
	
	public static ServletContextManager getInstance(){
		if(instance== null){
			instance = new ServletContextManager();
		}
		return instance;
	}
	
	public void setServletContext(ServletContext servletContext) {
	     this.servletContext = servletContext;
	}
	
	public String getRealPath(String relativePath){
		return servletContext.getRealPath(relativePath);
	}
}
