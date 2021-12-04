package com.customer.queue.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CounterOccupancy implements Serializable {
	@Id
	private Long counterNumber;
	private Long allocatedId;
	@Temporal(TemporalType.DATE)
	private Date applicationDate;
	private String branchCode;
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE); 
	}

}
