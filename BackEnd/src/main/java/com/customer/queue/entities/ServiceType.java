package com.customer.queue.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@SequenceGenerator(name = "ServiceTypeId_Seq")
//@SequenceGenerator(name = "ServiceTypeId_Seq", sequenceName = "service_type_id",allocationSize=1)
public class ServiceType {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ServiceTypeId_Seq")
	private Long serviceTypeId;
	private String serviceTypeMnemonic;
	private String serviceTypeDescription;
}
