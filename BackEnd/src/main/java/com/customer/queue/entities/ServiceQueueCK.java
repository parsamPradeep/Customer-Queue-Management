package com.customer.queue.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class ServiceQueueCK implements Serializable {

	@Temporal(TemporalType.DATE)
	private Date queueDate;
	private Integer tokenNumber;
	private String branchCodeCbs;

	public ServiceQueueCK() {}

	public ServiceQueueCK(Date queueDate,Integer tokenNumber,String branchCodeCbs) {
		this.queueDate = queueDate;
		this.tokenNumber = tokenNumber;
		this.branchCodeCbs=branchCodeCbs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((branchCodeCbs == null) ? 0 : branchCodeCbs.hashCode());
		result = prime * result + ((queueDate == null) ? 0 : queueDate.hashCode());
		result = prime * result + ((tokenNumber == null) ? 0 : tokenNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServiceQueueCK other = (ServiceQueueCK) obj;
		if (branchCodeCbs == null) {
			if (other.branchCodeCbs != null)
				return false;
		} else if (!branchCodeCbs.equals(other.branchCodeCbs))
			return false;
		if (queueDate == null) {
			if (other.queueDate != null)
				return false;
		} else if (!queueDate.equals(other.queueDate))
			return false;
		if (tokenNumber == null) {
			if (other.tokenNumber != null)
				return false;
		} else if (!tokenNumber.equals(other.tokenNumber))
			return false;
		return true;
	}



}
 