package com.customer.queue.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
public class TokenNumberForDate implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private TokenNumberForDateCK tokenNumberForDateCK;
    private Integer latestTokenNumberIssued;
}
