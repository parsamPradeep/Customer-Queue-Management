package com.customer.queue.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CounterOccupancy implements Serializable {
	@Id
	private Long counterNumber;
	private Long allocatedTellerId;
	@Temporal(TemporalType.DATE)
	private Date applicationDate;
	private String branchCodeCbs;

}
