package com.nusyn.license.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service("WebSocketController")
public class WebSocketController {

	@Autowired
	private SimpMessagingTemplate template;
	
	public void postTopic(String topic, String message){
		this.template.convertAndSend(topic,message);
	}
	
}
