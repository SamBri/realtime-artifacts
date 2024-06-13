package com.css.realtime.realtime_callbacks;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MessagingService implements IMessagingService {

	// monitor application events
	private final static Logger LOGGER = LoggerFactory.getLogger(MessagingService.class);

	// inject the repository here.
	
	public MessagingService(MessagesRepository messagesRepository) {
		super();
		this.messagesRepository = messagesRepository;
	}

	MessagesRepository messagesRepository;

	// inject the therapist rate repo here.




	@Override
	public MessagePayload createMessage(MessagePayloadDTO message) {

		
		
		
		MessagePayload msgPayload = messagesRepository.save(new MessagePayload(null,message.getUserId(),UUID.randomUUID(),message.getMessage(),null));
		
		if(msgPayload != null) {
			
		} else {
			
		}

		
		return msgPayload;
		
		
		
	}

	public List<MessagePayload> getAllMessages() {
	
	List<MessagePayload> list =	messagesRepository.findAll();
	
	return   list.isEmpty() ? null : list;
		
	}

	public List<MessagePayload> fetchMessagesByUserId(UUID userId) {
		List<MessagePayload> list =	messagesRepository.findMessagesByUserId(userId);
		
		return   list.isEmpty() ? null : list;

	}


	
}
