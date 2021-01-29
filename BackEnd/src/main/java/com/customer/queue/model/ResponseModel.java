package com.customer.queue.model;

import com.fasterxml.jackson.annotation.JsonRawValue;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseModel {
	@JsonRawValue
	private Object data;

	private String successDetails;
	private String errorDetails;
	private ResponseStatus responseStatus; 
	
}
