package com.customer.queue.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.customer.queue.entities.CounterOccupancy;

public interface CounterOccupancyRepo extends JpaRepository<CounterOccupancy,Long>{

	List<CounterOccupancy> findByBranchCode(String branchCode);
	Optional<CounterOccupancy> findByCounterNumber(Long counterNumber);
	Optional<CounterOccupancy> findByBranchCodeAndAllocatedId(String branchCode, Long allocatedId);

}
