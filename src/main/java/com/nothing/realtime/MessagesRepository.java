package com.nothing.realtime;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessagesRepository extends JpaRepository<MessagePayload, Long> {

	List<MessagePayload> findMessagesByUserId(UUID userId);

}
