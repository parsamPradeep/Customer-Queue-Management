package com.customer.queue.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.customer.queue.entities.Counter;

public interface CounterRepo extends JpaRepository<Counter,Long>{
List<Counter> findByBranchCode(String branchCode);
Optional<Counter> findByCounterId(Long counterId);
Optional<Counter> findByCounterDescription(String counterDescription);
List<Counter> deleteByCounterId(Long counterId);
List<Counter> findByCounterIdNotIn(List<Long> counterId);
}
