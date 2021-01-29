package com.customer.queue.entities;

import java.io.Serializable;
import java.util.Date;

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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((counterId == null) ? 0 : counterId.hashCode());
		result = prime * result + ((serviceTypeId == null) ? 0 : serviceTypeId.hashCode());
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
		ServiceTypeForCounterCK other = (ServiceTypeForCounterCK) obj;
		if (counterId == null) {
			if (other.counterId != null)
				return false;
		} else if (!counterId.equals(other.counterId))
			return false;
		if (serviceTypeId == null) {
			if (other.serviceTypeId != null)
				return false;
		} else if (!serviceTypeId.equals(other.serviceTypeId))
			return false;
		return true;
	}

}
