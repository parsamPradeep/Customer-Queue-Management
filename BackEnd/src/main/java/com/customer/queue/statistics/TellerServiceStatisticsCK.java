package com.customer.queue.statistics;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;
@Embeddable
@Setter
@Getter

public class TellerServiceStatisticsCK implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private Long tellerId;
    @Temporal(TemporalType.DATE)
    private Date branchDate;
    private Long serviceTypeId;
    private String branchCode; 
}
