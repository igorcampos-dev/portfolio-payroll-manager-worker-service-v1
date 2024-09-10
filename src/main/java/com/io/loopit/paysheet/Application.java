package com.io.loopit.paysheet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@ComponentScan(basePackages = {"com.io.loopit.paysheet", "com.nexus"})
public class Application {

	public static void main(String[] args) {
		run();
	}

	public static void run(){
		SpringApplication.run(Application.class);
	}

}
