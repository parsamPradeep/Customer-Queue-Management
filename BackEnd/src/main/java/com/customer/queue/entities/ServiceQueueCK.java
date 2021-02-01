package com.customer.queue.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class ServiceQueueCK implements Serializable {

	@Temporal(TemporalType.DATE)
	private Date queueDate;
	private Integer tokenNumber;
	private String branchCode;

	public ServiceQueueCK() {}

	public ServiceQueueCK(Date queueDate,Integer tokenNumber,String branchCode) {
		this.queueDate = queueDate;
		this.tokenNumber = tokenNumber;
		this.branchCode=branchCode;
	}

	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE); 
	}


}
 