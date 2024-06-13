package com.css.realtime.realtime_callbacks;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;



@Component
public class MyStreamingBackgroundTask<T> implements Runnable  {

	private ResponseBodyEmitter emitter;
	
	
	private UUID userId;
	

	@Autowired
	IMessagingService msgService;
	
	public MyStreamingBackgroundTask(ResponseBodyEmitter emitter) {
		super();
		this.emitter = emitter;
	}

	

	public void setEmitter(ResponseBodyEmitter emitter) {
		this.emitter = emitter;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public IMessagingService getMsgService() {
		return msgService;
	}

	public void setMsgService(IMessagingService msgService) {
		this.msgService = msgService;
	}

	public MyStreamingBackgroundTask() {
	}
	
	
	
	public MyStreamingBackgroundTask(ResponseBodyEmitter emitter, UUID userId) {
		super();
		this.emitter = emitter;
		this.userId = userId;
	}





	@Override
	public void run() {

		System.out.println("background processing running");

		try {

			List<MessagePayload> list = msgService.fetchMessagesByUserId(userId);

			if (list != null) {

				System.out.println("list of messages found " + list.size());
				for (MessagePayload msg : list) {
					emitter.send(msg.getMessage(), MediaType.APPLICATION_OCTET_STREAM);
					System.out.println("emitted");
					
				}
			}

//			// and again later on
//			emitter.send("Hello again");

			// and done at some point
			emitter.complete();

		} catch (Exception e) {

			System.out.println("Exception generated :: " + e);
		
		}
		// In some other thread

	}

	
	
	
}
