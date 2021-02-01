package com.customer.queue.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@IdClass(ServiceTypeForCounterCK.class)
public class ServiceTypeForCounter implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	private Long counterId;
	@Id
	private Long serviceTypeId;
	@Transient
	private String description;
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE); 
	}
	

}
