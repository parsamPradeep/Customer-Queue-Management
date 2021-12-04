package com.customer.queue.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseModel {

	private Object data;
	private String successDetails;
	private String errorDetails;
	private ResponseStatus responseStatus; 
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE); 
	}
	
}
