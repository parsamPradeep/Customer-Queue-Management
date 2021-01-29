package com.customer.queue.entities;

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
public class TokenNumberForDateCK implements Serializable {
	@Temporal(TemporalType.DATE)
    private Date tokenDate;
	private String branchCodeCbs;
}
