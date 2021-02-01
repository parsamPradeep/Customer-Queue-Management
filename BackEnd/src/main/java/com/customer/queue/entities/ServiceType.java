package com.customer.queue.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Entity
@Getter
@Setter
@SequenceGenerator(name = "ServiceTypeId_Seq")
public class ServiceType {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ServiceTypeId_Seq")
	private Long serviceTypeId;
	private String serviceTypeMnemonic;
	private String serviceTypeDescription;
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE); 
	}
}
