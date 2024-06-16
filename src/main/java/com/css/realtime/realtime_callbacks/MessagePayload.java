package com.css.realtime.realtime_callbacks;

import java.time.ZonedDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "messages") // A FOLDER OF messages.
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessagePayload {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	@Column(name = "user_id")
	private UUID userId;

	@Column(name = "message_id")
	private UUID messageId;

	@Column(name = "message")
	private String message;

	@CreationTimestamp
	@Column(name = "date_created", nullable = false)
	private ZonedDateTime dateCreated;

}
