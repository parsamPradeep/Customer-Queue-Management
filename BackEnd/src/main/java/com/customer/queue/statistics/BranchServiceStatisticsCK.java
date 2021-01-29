package com.customer.queue.statistics;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;
@Embeddable
@Getter
@Setter
public class BranchServiceStatisticsCK  implements Serializable {
	  private static final long serialVersionUID = 1L;
	  	private String branchCodeCbs;
	  	@Temporal(TemporalType.DATE)
	    private Date branchDate;
	    private Long serviceTypeId;
    
}
