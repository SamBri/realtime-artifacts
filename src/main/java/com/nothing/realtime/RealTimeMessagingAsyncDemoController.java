package com.nothing.realtime;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.Scope;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@RestController
@RequestMapping("/api/async/demo")
public class RealTimeMessagingAsyncDemoController {

	@Autowired
	MyStreamingBackgroundTask<String> myStreamingBackgroundTask;

	@Autowired
	MyMomentsStreamingBackgroundTask myMomentsStreamingBackgroundTask;

	@Autowired
	MyOwnSSeEmitter myOwnSSeEmitter;

	@PostMapping("/messages")
	public Callable<String> processUpload() {
		return () -> "someView";
	}

	@GetMapping("/quotes")
	@ResponseBody
	public DeferredResult<String> quotes() {
		DeferredResult<String> deferredResult = new DeferredResult<>();
		// Save the deferredResult somewhere.
		MyWorkerThread<String> backgroundThread = new MyWorkerThread<String>(deferredResult, "Hello");
		backgroundThread.start();

		return deferredResult;
	}

	/**
	 * HTTP streaming streaming of objects
	 * 
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
	 * HTTP Streaming SSE Emitter Server Sent Events
	 * 
	 * @return
	 */
	@GetMapping(path = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public SseEmitter handle() {
		SseEmitter emitter = new SseEmitter();
		// Save the emitter somewhere..
		myStreamingBackgroundTask.setEmitter(emitter);
		// myStreamingBackgroundTask.setUserId(userId);
		Thread streamingThread = new Thread(myStreamingBackgroundTask);
		streamingThread.start();
		return emitter;
	}

	/**
	 * HTTP Streaming SSE Emitter Server Sent Events
	 * 
	 * @return
	 */
	@GetMapping(path = "/streams/moments", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public SseEmitter handle(@RequestParam String dateTime) {

		SseEmitter emitter = new SseEmitter(86_400_000L);

		// RealTimeMessagingExceptionHandler.copyEmitter(emitter);
		try {

			System.out.println("The default timeout ::" + emitter.getTimeout());

			myMomentsStreamingBackgroundTask.setEmitter(emitter);
			myMomentsStreamingBackgroundTask.setMoment(dateTime);
			Thread streamingThread = new Thread(myMomentsStreamingBackgroundTask);
			streamingThread.start();
		} catch (Exception e) {

			System.err.println("INSIDE streaming exception : " + e);

		}

		return emitter;
	}

	// movie streaming.
//	@GetMapping(path = "/streams/movies", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
//	public ResponseEntity<SseEmitter> streamMovie() {
//
//		try {
//
//			MyMovieStreamingTask movieStreamTask = new MyMovieStreamingTask(myOwnSSeEmitter);
//			Thread watchMovie = new Thread(movieStreamTask);
//			watchMovie.start();
//		} catch (Exception e) {
//
//			System.err.println("INSIDE streaming exception : " + e);
//
//		}
//
//		HttpHeaders headers = new HttpHeaders();
//		// video/mp4; codecs="avc1.4d002a"
//		//headers.add("Content-Type", "video/mp4");
//		ResponseEntity<SseEmitter> movieResponseEntity = new ResponseEntity<SseEmitter>(myOwnSSeEmitter,headers,HttpStatus.OK);
//		
//		return movieResponseEntity;
//	}
//	
	
	@GetMapping("/download")
	public ResponseEntity<StreamingResponseBody> streamHandle() {
		
		
		File movie = new File("howlongdemo.mp4");
		File dst = new File("streamed.mp4");
		StreamingResponseBody	streamResponse = (out) -> {
			try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(movie));
					) {

				// buffered reading of movie
				byte[] buffer = new byte[1024];

				int frames; // 0
				while ((frames = inputStream.read(buffer)) > 0) {

			

					out.write(buffer, 0, frames);

				}


			} catch (Exception e) {

				System.out.println("exception "+ e);
			}
			
			
		

		};
		
		try(BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(dst))){
			
			
			streamResponse.writeTo(outputStream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	  // StreamingResponseBody	streamResponse;
		
		HttpHeaders headers = new HttpHeaders();
		//headers.set("Content-Type", "video/mp4");
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

		ResponseEntity<StreamingResponseBody> responseEntity = new ResponseEntity<>(streamResponse,HttpStatus.OK);
		
			return responseEntity;
	}

	
	
	@GetMapping("/downloadfile")
	public ResponseEntity<Object> streamHandle2() {
		
		
		File movie = new File("howlongdemo.mp4");
		File dst = new File("streamed.mp4");
		StreamingResponseBody	streamResponse = (out) -> {
			try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(movie));
					) {

				// buffered reading of movie
				byte[] buffer = new byte[1024];

				int frames; // 0
				while ((frames = inputStream.read(buffer)) > 0) {

			

					out.write(buffer, 0, frames);

				}


			} catch (Exception e) {

				System.out.println("exception "+ e);
			}
			
			
		

		};
		BufferedOutputStream saveBufferedOutputStream =null;

		try(BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(dst))){
			
			
			streamResponse.writeTo(outputStream);
			saveBufferedOutputStream = outputStream;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	  // StreamingResponseBody	streamResponse;
		
		HttpHeaders headers = new HttpHeaders();
	   // headers.set("Content-Type", "video/mp4");
	   // headers.set("Content-Type", "application/");
	    
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

		
		ResponseEntity<Object> responseEntity = new ResponseEntity<>(dst,headers,HttpStatus.OK);
		
			return responseEntity;
	}
}
