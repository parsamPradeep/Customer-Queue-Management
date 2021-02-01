package com.customer.queue.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.customer.queue.statistics.BranchServiceStatistics;
import com.customer.queue.statistics.BranchServiceStatisticsCK;

@Repository
public interface BranchServiceStatisticsRepo extends JpaRepository<BranchServiceStatistics,Long> {
	Optional<BranchServiceStatistics>findByBranchServiceStatisticsCK(BranchServiceStatisticsCK branchServiceStatisticsCK);
};
