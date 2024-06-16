package com.css.realtime.realtime_callbacks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.context.request.async.DeferredResult;

public class MyWorkerThread<T> extends Thread {

	private DeferredResult<T> deferredResult;

	private T messages;

	public MyWorkerThread(DeferredResult<T> deferredResult, T messages) {
		super();
		this.deferredResult = deferredResult;
		this.messages = messages;
	}

	public MyWorkerThread() {
		super();
	}

	public DeferredResult<T> getDeferredResult() {
		return deferredResult;
	}

	public void setDeferredResult(DeferredResult<T> deferredResult) {
		this.deferredResult = deferredResult;
	}

	public T getMessages() {
		return messages;
	}

	public void setMessages(T messages) {
		this.messages = messages;
	}

	@Override
	public void run() {

		System.out.println("background processing running");

		deferredResult = getDeferredResult();

		deferredResult.setResult(messages);

	}

}
