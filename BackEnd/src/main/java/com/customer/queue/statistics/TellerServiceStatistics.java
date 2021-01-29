package com.customer.queue.statistics;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
@Entity
@Setter
@Getter

public class TellerServiceStatistics implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private TellerServiceStatisticsCK tellerServiceStatisticsCK;
   
	
	private Integer numberServiced;
    private Integer numberRejected;
    private Integer averageServiceCompletionTimeInSeconds;
    private Integer averageServiceRejectionTimeInSeconds;
    private Integer highestServiceTimeInSeconds;
    private Integer lowestServiceTimeInSeconds;

}
