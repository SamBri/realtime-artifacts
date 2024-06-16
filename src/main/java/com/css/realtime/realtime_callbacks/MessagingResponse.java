package com.css.realtime.realtime_callbacks;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MessagingResponse<T> {

	private int code;
	private String status;
	private T response;

	@JsonProperty("responseMessage")
	private String message;
	private ZonedDateTime timestamp;

}
