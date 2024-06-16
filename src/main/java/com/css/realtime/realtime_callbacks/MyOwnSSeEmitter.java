package com.css.realtime.realtime_callbacks;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public class MyOwnSSeEmitter extends SseEmitter {

	public MyOwnSSeEmitter() {
		super();
	}

	private MyOwnSSeEmitter(Long timeout) {
		super(timeout);
	}

	public static MyOwnSSeEmitter createCustomTimeOutSSeEmitter(Long timeout) {
		return new MyOwnSSeEmitter(timeout);
	}

}
