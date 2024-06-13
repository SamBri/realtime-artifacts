package com.css.realtime.realtime_callbacks;

import java.util.UUID;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/async/demo")
public class RealTimeMessagingAsyncDemoController {
	
	
	@Autowired
	MyStreamingBackgroundTask<String> myStreamingBackgroundTask;
	
	
	
	@PostMapping("/messages")
	public Callable<String> processUpload() {
		return () -> "someView";
	}
	
	@GetMapping("/quotes")
	@ResponseBody
	public DeferredResult<String> quotes() {
		DeferredResult<String> deferredResult = new DeferredResult<>();
		// Save the deferredResult somewhere.
		MyWorkerThread<String> backgroundThread = new MyWorkerThread<String>(deferredResult,"Hello");
		backgroundThread.start();
		
		return deferredResult;
	}
	
	
	/**
	 * HTTP streaming 
	 * streaming of objects
	 * @param userId
	 * @return
	 */
	@GetMapping("/streams/users/{userId}/messages")
	public ResponseBodyEmitter handle(@PathVariable UUID userId) {
		ResponseBodyEmitter emitter = new ResponseBodyEmitter();		
		

		myStreamingBackgroundTask.setEmitter(emitter);
		myStreamingBackgroundTask.setUserId(userId);
		Thread streamingThread = new Thread(myStreamingBackgroundTask);
		streamingThread.start();
		return emitter;
		
		
	}
	
	
	/**
	 * HTTP Streaming
	 * SSE Emitter
	 * Server Sent Events
	 * @return
	 */
	@GetMapping(path="/events", produces=MediaType.TEXT_EVENT_STREAM_VALUE)
	public SseEmitter handle() {
		SseEmitter emitter = new SseEmitter();
		// Save the emitter somewhere..
		myStreamingBackgroundTask.setEmitter(emitter);
		//myStreamingBackgroundTask.setUserId(userId);
		Thread streamingThread = new Thread(myStreamingBackgroundTask);
		streamingThread.start();
		return emitter;
	}
	
	

	

}
