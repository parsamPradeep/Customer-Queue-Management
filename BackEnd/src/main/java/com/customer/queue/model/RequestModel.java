package com.customer.queue.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestModel {
	private Date applicationDate;
	private Object data;
	private String branchCode;
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE); 
	}
}
