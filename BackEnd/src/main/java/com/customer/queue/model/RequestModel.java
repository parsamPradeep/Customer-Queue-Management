package com.customer.queue.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestModel {
	private Date applicationDate;
	private Object Data;
	private String branchCode;
	private ResponseStatus responseStatus;
}
