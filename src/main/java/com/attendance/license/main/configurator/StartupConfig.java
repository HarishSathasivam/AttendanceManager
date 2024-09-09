package com.nusyn.license.main.configurator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nusyn.license.startup.StartUpController;

@Configuration
public class StartupConfig {
	 @Bean
	    public StartUpController startUpController() {
	        return new StartUpController();
	    }
}


