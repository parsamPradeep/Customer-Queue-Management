package com.customer.queue.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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
    @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE); 
	}
}
