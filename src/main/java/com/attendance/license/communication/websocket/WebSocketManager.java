package com.nusyn.license.communication.websocket;

import org.apache.log4j.Logger;

import com.nusyn.license.exception.StartupException;
import com.nusyn.license.main.controller.WebSocketController;
import com.nusyn.license.manager.SpringContextManager;

public class WebSocketManager {

	private static WebSocketManager instance = null;
	private boolean running = false;
	
	protected Logger logger = Logger.getLogger(WebSocketManager.class.getName());
	
	public static enum WebSocketTopics {
		AddTrip("/topic/addTrip"), 
		RemoveTrip("/topic/removeTrip"), 
		UpdateTripStatus("/topic/updateTripStatus");
		
	    private String topicName = "";
	    
	    WebSocketTopics(String topicName)
	    {
	    	this.topicName = topicName;
	    }

		public String getTopicName() {
			return topicName;
		}

		public void setTopicName(String topicName) {
			this.topicName = topicName;
		}
	    
	}
	
	private WebSocketController webSocketController = null;
	
	private WebSocketManager(){
		// restricted from outside access
	}
	
	public static WebSocketManager getInstance(){
		if(instance == null){
			instance = new WebSocketManager();
		}
		return instance;
	}
	
	public void init(){
		webSocketController = (WebSocketController) SpringContextManager.getInstance().getSpringBean("WebSocketController");
	}

	public void start() throws StartupException{
		try
		{
			running = true;
		}catch(Exception exp){
			running = false;
			throw new StartupException("Exception starting up SheduleManager " + exp.getMessage(), exp);
		}
	}
	public void stop(){
		running = false;
	}
	
	public WebSocketController getWebSocketController() {
		return webSocketController;
	}

	public void setWebSocketController(WebSocketController webSocketController) {
		this.webSocketController = webSocketController;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
	
	
}
