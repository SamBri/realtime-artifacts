package com.css.realtime.realtime_callbacks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/realtime")
public class RealTimeMessagingController {
	
	@Autowired 
	IMessagingService msgService;
	
	

	
	@PostMapping("/messages")
	public ResponseEntity<MessagingResponse<MessagePayload>> createMessage(@RequestBody MessagePayloadDTO message) {
		
		  ResponseEntity<MessagingResponse<MessagePayload>> createMessageResponse = null;
		
		try {
			// when the message get created.
			   MessagePayload theCreatedMsgPayload = msgService.createMessage(message);
		
			   
			   if(theCreatedMsgPayload != null) {
				   
				   
				   // kick start the real time feed
				   
				   MessagingResponse<MessagePayload> successMessageResponse = new MessagingResponse<>();
				   successMessageResponse.setCode(200);
				   successMessageResponse.setMessage("message created");
				   successMessageResponse.setResponse(theCreatedMsgPayload);
				   successMessageResponse.setStatus("Success");
				   successMessageResponse.setTimestamp(ZonedDateTime.now());
				   
				   createMessageResponse = new ResponseEntity<MessagingResponse<MessagePayload>>(successMessageResponse, HttpStatus.OK);
				   
				   
			   }
				
			   else { 
			   MessagingResponse<MessagePayload> errorMessageResponse = new MessagingResponse<>();
			   errorMessageResponse.setCode(200);
			   errorMessageResponse.setResponse(theCreatedMsgPayload);
			   errorMessageResponse.setStatus("error");
			   errorMessageResponse.setTimestamp(ZonedDateTime.now());
			   
			   createMessageResponse = new ResponseEntity<MessagingResponse<MessagePayload>>(errorMessageResponse, HttpStatus.OK);
			   }
			
		} catch(Exception e) {
			
			
		}

		
		
		return createMessageResponse;
		
	}
	
	
	
	@GetMapping("/messages")
	public ResponseEntity<MessagingResponse<List<MessagePayload>>> getAllMessages() {
		
		
		   
		  ResponseEntity<MessagingResponse<List<MessagePayload>>> allMessagesResponse = null;
		
		try {
			// when the message get created.
			   List<MessagePayload> list = msgService.getAllMessages();
		
			   
			   if(list != null) {
				   
				   
				   // kick start the real time feed
				   
				   MessagingResponse<List<MessagePayload>> successMessageResponse = new MessagingResponse<>();
				   successMessageResponse.setCode(200);
				   successMessageResponse.setMessage("list of messages retrieved successfully");
				   successMessageResponse.setResponse(list);
				   successMessageResponse.setStatus("Success");
				   successMessageResponse.setTimestamp(ZonedDateTime.now());
				   
				   allMessagesResponse = new ResponseEntity<MessagingResponse<List<MessagePayload>>>(successMessageResponse, HttpStatus.OK);
				   
				   
			   }
				
			   else { 
			   MessagingResponse<List<MessagePayload>> errorMessageResponse = new MessagingResponse<>();
			   errorMessageResponse.setCode(200);
			   errorMessageResponse.setResponse(list);
			   errorMessageResponse.setStatus("error");
			   errorMessageResponse.setTimestamp(ZonedDateTime.now());
			   
			   allMessagesResponse = new ResponseEntity<MessagingResponse<List<MessagePayload>>>(errorMessageResponse, HttpStatus.OK);
			   }
			
		} catch(Exception e) {
			
			
		}

		
		
		return allMessagesResponse;
		
	}
	
	
	
	
	@GetMapping("/messages/{userId}/users")
	public ResponseEntity<MessagingResponse<List<MessagePayload>>> getAllMessagesByUserId(@PathVariable UUID userId) {
		
		
		   
		  ResponseEntity<MessagingResponse<List<MessagePayload>>> allMessagesByUserIdResponse = null;
		
		try {
			// when the message get created.
			   List<MessagePayload> list = msgService.fetchMessagesByUserId(userId);
		
			   
			   if(list != null) {
				   
				   
				   // kick start the real time feed
				   
				   MessagingResponse<List<MessagePayload>> successMessageResponse = new MessagingResponse<>();
				   successMessageResponse.setCode(200);
				   successMessageResponse.setMessage("list of user messages retrieved successfully");
				   successMessageResponse.setResponse(list);
				   successMessageResponse.setStatus("Success");
				   successMessageResponse.setTimestamp(ZonedDateTime.now());
				   
				   allMessagesByUserIdResponse = new ResponseEntity<MessagingResponse<List<MessagePayload>>>(successMessageResponse, HttpStatus.OK);
				   
				   
			   }
				
			   else { 
			   MessagingResponse<List<MessagePayload>> errorMessageResponse = new MessagingResponse<>();
			   errorMessageResponse.setCode(200);
			   errorMessageResponse.setResponse(list);
			   errorMessageResponse.setStatus("error");
			   errorMessageResponse.setTimestamp(ZonedDateTime.now());
			   
			   allMessagesByUserIdResponse = new ResponseEntity<MessagingResponse<List<MessagePayload>>>(errorMessageResponse, HttpStatus.OK);
			   }
			
		} catch(Exception e) {
			
			
		}

		
		
		return allMessagesByUserIdResponse;
		
	}
	
	
	
	
	@GetMapping("/users/:userId/callbacks")
	public String pushTime(@RequestParam String userId) {
		
		
		LocalDateTime today = LocalDateTime.now();
		System.out.println("today ::" + today);
		LocalDateTime submissionDateTime = LocalDateTime.parse("2024-05-28T15:51:31.481");
		System.out.println("submissionDateTime ::"+submissionDateTime);
		LocalDateTime sTime = LocalDateTime.now().plusMonths(12);
		System.out.println(sTime);
		Duration howLongD = Duration.between(LocalDateTime.now(), submissionDateTime);
				


		System.out.println(howLongD.abs());
		// PT-3H-30M-41.6589405S
		/*
		 * long hours = howLongD.get(ChronoUnit.HOURS); long seconds =
		 * howLongD.get(ChronoUnit.SECONDS); long day = howLongD.get(ChronoUnit.DAYS);
		 * long week = howLongD.get(ChronoUnit.WEEKS); long month =
		 * howLongD.get(ChronoUnit.MONTHS); long year = howLongD.get(ChronoUnit.YEARS);
		 */

		String message = MyDateUtils.parsePostDuration(howLongD.abs());

		System.out.println(message);
		
		return message;
		
	}
	
	
	

}
