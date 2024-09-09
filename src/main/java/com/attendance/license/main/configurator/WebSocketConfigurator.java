package com.nusyn.license.main.configurator;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import org.springframework.messaging.simp.config.MessageBrokerRegistry;

//Test 123 456 678 999 111 222 333 444 555 777 999 1010 1111
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfigurator extends AbstractWebSocketMessageBrokerConfigurer{

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/mmiWebSocket").withSockJS();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config){
		config.enableSimpleBroker("/topic");
		config.setApplicationDestinationPrefixes("/app");	
	}
}
