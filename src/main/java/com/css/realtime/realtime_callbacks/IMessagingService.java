package com.css.realtime.realtime_callbacks;

import java.util.List;
import java.util.UUID;

public interface IMessagingService {
	
	MessagePayload createMessage(MessagePayloadDTO message);
	
	 List<MessagePayload> fetchMessagesByUserId(UUID userId);

	List<MessagePayload> getAllMessages();

}
