package com.nothing.realtime;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@SpringBootApplication
public class RealtimeCallbacksApplication {

	public static void main(String[] args) {
		SpringApplication.run(RealtimeCallbacksApplication.class, args);
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	MyOwnSSeEmitter myOwnSSeEmitter() {
		return MyOwnSSeEmitter.createCustomTimeOutSSeEmitter(5000L);
	}

}
