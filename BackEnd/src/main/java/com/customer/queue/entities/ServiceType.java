package com.customer.queue.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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
	private String branchCode; 
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE); 
	}
}
