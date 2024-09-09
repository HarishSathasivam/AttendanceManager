package com.nusyn.license.main;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;



@SpringBootApplication
@EntityScan("com.nusyn.license.main.access.pojo") 

public class Main extends SpringBootServletInitializer
{
	
	
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Main.class);
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Main.class, args);
	}
}
