package com.customer.queue.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.customer.queue.entities.ServiceTypeForCounter;

public interface ServiceTypeForCounterRepo extends JpaRepository<ServiceTypeForCounter, Long>{
	               
	Optional<ServiceTypeForCounter> findByCounterIdAndServiceTypeId(Long conterId, Long serviceTypeId);
	List<ServiceTypeForCounter> findByCounterId(Long conterId);
	Optional<ServiceTypeForCounter> deleteByCounterIdAndServiceTypeId(Long conterId, Long serviceTypeId);
}
