package com.customer.queue.repos;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.customer.queue.entities.TokenNumberForDate;
import com.customer.queue.entities.TokenNumberForDateCK;


public interface TokenNumberForDateRepo extends JpaRepository<TokenNumberForDate,Integer> {
	//Optional<TokenNumberForDate> findByTokenNumberForDateCKTokenDateAndLatestTokenNumberIssued(Date tokenDate,Integer latestTokenNumberIssued);

	Optional<TokenNumberForDate> findByTokenNumberForDateCK(TokenNumberForDateCK tokenNumberForDateCK);
}
