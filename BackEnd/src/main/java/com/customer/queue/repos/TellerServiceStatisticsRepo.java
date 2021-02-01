package com.customer.queue.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.customer.queue.statistics.TellerServiceStatistics;
import com.customer.queue.statistics.TellerServiceStatisticsCK;

@Repository
public interface TellerServiceStatisticsRepo extends JpaRepository<TellerServiceStatistics,Long> {

	Optional<TellerServiceStatistics> findByTellerServiceStatisticsCK(
			TellerServiceStatisticsCK tellerServiceStatisticsCK);
	
	
}
