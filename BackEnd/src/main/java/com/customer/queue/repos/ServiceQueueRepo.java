package com.customer.queue.repos;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.customer.queue.constants.CustomerQueueStatus;
import com.customer.queue.entities.ServiceQueue;

@Repository
public interface ServiceQueueRepo extends JpaRepository<ServiceQueue, Long> {
	Optional<ServiceQueue> findByCounterNumberAndCustomerQueueStatus(int counterNumber, CustomerQueueStatus allocated);

	Optional<ServiceQueue> findByQueueDateAndCounterNumberAndCustomerQueueStatusAndBranchCode(Date queueDate,
			Long counterNumber, CustomerQueueStatus allocated, String branchCode);

	Optional<ServiceQueue> findFirstByCustomerQueueStatusAndBranchCodeAndQueueDateAndServiceTypeIdInOrderByQueueTimeStampAsc(
			CustomerQueueStatus pending, String branchCode, Date queueDate, List<Long> serviceTypeId);

	Optional<ServiceQueue> findByQueueDateAndBranchCodeAndServiceTypeIdAndCustomerQueueStatus(Date queueDate,
			String branchCode, Long serviceTypeId, CustomerQueueStatus CustomerQueueStatus);

	Optional<ServiceQueue> findByQueueDateAndBranchCodeAndServiceTypeIdAndCustomerQueueStatusAndAllocatedId(
			Date queueDate, String branchCode, Long serviceTypeId, CustomerQueueStatus CustomerQueueStatus,
			Long allocatedId);

	Integer countByQueueDateAndBranchCodeAndServiceTypeIdAndCustomerQueueStatusIn(Date queueDate, String branchCode,
			Long serviceTypeId, List<CustomerQueueStatus> CustomerQueueStatus);

	Integer countByQueueDateAndBranchCodeAndCustomerQueueStatusAndServiceTypeId(Date queueDate, String branchCode,
			CustomerQueueStatus CustomerQueueStatus, Long serviceTypeId);

	Integer countByQueueDateAndBranchCodeAndCustomerQueueStatusAndAllocatedIdAndServiceTypeId(Date queueDate,
			String branchCode, CustomerQueueStatus CustomerQueueStatus, Long allocatedId, Long serviceTypeId);

	Integer countByQueueDateAndBranchCodeAndCustomerQueueStatusAndCounterNumber(Date queueDate, String branchCode,
			CustomerQueueStatus CustomerQueueStatus, Long counterNumber);

	Optional<ServiceQueue> findFirstByQueueDateAndBranchCodeAndCustomerQueueStatusAndServiceTypeIdOrderByServicedTimeInSecDesc(
			Date queueDate, String branchCode, CustomerQueueStatus CustomerQueueStatus, Long serviceTypeId);

	Optional<ServiceQueue> findFirstByQueueDateAndBranchCodeAndCustomerQueueStatusAndServiceTypeIdOrderByServicedTimeInSecAsc(
			Date queueDate, String branchCodeCbs, CustomerQueueStatus CustomerQueueStatus, Long serviceTypeId);

	Optional<ServiceQueue> findFirstByQueueDateAndBranchCodeAndCustomerQueueStatusAndServiceTypeIdAndAndAllocatedIdOrderByServicedTimeInSecDesc(
			Date queueDate, String branchCode, CustomerQueueStatus CustomerQueueStatus, Long serviceTypeId,
			Long allocatedTellerId);

	Optional<ServiceQueue> findFirstByQueueDateAndBranchCodeAndCustomerQueueStatusAndServiceTypeIdAndAndAllocatedIdOrderByServicedTimeInSecAsc(
			Date queueDate, String branchCode, CustomerQueueStatus CustomerQueueStatus, Long serviceTypeId,
			Long allocatedTellerId);

	List<ServiceQueue> findByQueueDateAndBranchCodeAndCustomerQueueStatusAndCounterNumber(Date queueDate,
			String branchCode, CustomerQueueStatus CustomerQueueStatus, Long counterNumber);

	List<ServiceQueue> findByQueueDateAndBranchCode(Date queueDate, String branchCode);

	Optional<ServiceQueue> findByQueueDateAndBranchCodeAndTokenNumberAndCustomerQueueStatus(Date queueDate,
			String branchCode, Integer tokenNumber, CustomerQueueStatus CustomerQueueStatus);

	Optional<ServiceQueue> findFirstByCustomerQueueStatusAndBranchCodeAndQueueDateOrderByQueueTimeStampAsc(
			CustomerQueueStatus pending, String branchCode, Date queueDate);

	Optional<ServiceQueue> findFirstByCustomerQueueStatusAndBranchCodeAndQueueDateAndCounterNumber(
			CustomerQueueStatus allocated, String branchCode, Date queueDate, Long counterNumber);

	List<ServiceQueue> findByQueueDateAndBranchCodeAndCustomerQueueStatus(Date queueDate, String branchCode,
			CustomerQueueStatus CustomerQueueStatus);

	List<ServiceQueue> findByQueueDateAndBranchCodeAndCounterNumberAndCustomerQueueStatus(Date queueDate,
			String branchCode, Long counterNumber, CustomerQueueStatus CustomerQueueStatus);

}
