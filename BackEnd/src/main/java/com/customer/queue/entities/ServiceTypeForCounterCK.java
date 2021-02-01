package com.customer.queue.entities;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class ServiceTypeForCounterCK implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long counterId;
	private Long serviceTypeId;

	public ServiceTypeForCounterCK() {}

	public ServiceTypeForCounterCK(Long counterId, Long serviceTypeId) {
		super();
		this.counterId = counterId;
		this.serviceTypeId = serviceTypeId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE); 
	}

}
