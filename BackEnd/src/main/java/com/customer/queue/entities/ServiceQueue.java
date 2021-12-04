package com.customer.queue.entities;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.customer.queue.constants.CustomerQueueStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

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
    private String branchCode;
	private String customerName;
    private String customerMobileNumber;
    private Long serviceTypeId;
    private String userName;
    @JsonFormat(pattern="HH:mm:ss.SSS")
    private Time queueTimeStamp;
    @Enumerated(EnumType.STRING)
    private CustomerQueueStatus customerQueueStatus;
    private Long allocatedId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
    private Timestamp allocationTimeStamp;
    @JsonFormat(pattern="HH:mm:ss.SSS")
    private Time serviceCompletionTimeStamp;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
    private Timestamp rejectionTimeStamp;
    private Long counterNumber;
    private Integer servicedTimeInSec;
    private Integer rejectedTimeInSec;


	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE); 
	}
}
