package com.customer.queue.entities;

import com.customer.queue.constants.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Getter
@Setter
@IdClass(ServiceQueueCK.class)
public class ServiceQueue implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Temporal(TemporalType.DATE)
	private Date queueDate;
	@Id
    private Integer tokenNumber;
	@Id
    private String branchCodeCbs;
	private String customerName;
    private String customerMobileNumber;
    private Long serviceTypeId;
    private String userName;
    @JsonFormat(pattern="HH:mm:ss.SSS")
    private Time queueTimeStamp;
    @Enumerated(EnumType.STRING)
    private TellerQueueStatus tellerQueueStatus;
    private Long allocatedTellerId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
    private Timestamp allocationTimeStamp;
    @JsonFormat(pattern="HH:mm:ss.SSS")
    private Time serviceCompletionTimeStamp;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
    private Timestamp rejectionTimeStamp;
    private Long counterNumber;
    private Integer servicedTimeInSec;
    private Integer rejectedTimeInSec;
    private String customerIdCbs;
    private String accountNumberCbs;
    private String nationalId;

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
		ServiceQueue other = (ServiceQueue) obj;
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
