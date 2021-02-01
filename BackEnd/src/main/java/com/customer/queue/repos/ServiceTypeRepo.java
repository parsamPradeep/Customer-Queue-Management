package com.customer.queue.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.customer.queue.entities.ServiceType;


public interface ServiceTypeRepo extends JpaRepository<ServiceType, Long>{
	
	List<ServiceType> deleteByServiceTypeId(Long serviceTypeId);
	Optional<ServiceType> findByServiceTypeId(Long serviceTypeId);
	Optional<ServiceType> findByServiceTypeDescription(String serviceTypeDescription);
}
