package com.customer.queue.services;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.customer.queue.codes.ErrorCodes;
import com.customer.queue.constants.CustomerQueueStatus;
import com.customer.queue.entities.ServiceQueue;
import com.customer.queue.entities.ServiceType;
import com.customer.queue.entities.ServiceTypeForCounter;
import com.customer.queue.entities.TokenNumberForDate;
import com.customer.queue.repos.ServiceQueueRepo;
import com.customer.queue.repos.ServiceTypeForCounterRepo;
import com.customer.queue.repos.ServiceTypeRepo;
import com.customer.queue.statistics.BranchServiceStatistics;
import com.customer.queue.statistics.TellerServiceStatistics;

@Transactional(rollbackFor = Exception.class)
@Service
public class CustomerQueueService {
	private Logger logger = LoggerFactory.getLogger(CustomerQueueService.class);
	private ServiceQueueRepo serviceQueueRepo;
	private CustomerQueueServiceUtility customerQueueServiceUtility;
	private ServiceTypeForCounterRepo serviceTypeForCounterRepo;
	private ServiceTypeRepo serviceTypeRepo;

	@Autowired
	public CustomerQueueService(ServiceQueueRepo serviceQueueRepo, 
			CustomerQueueServiceUtility customerQueueServiceUtility, 
			ServiceTypeForCounterRepo serviceTypeForCounterRepo, ServiceTypeRepo serviceTypeRepo) {
		this.serviceQueueRepo = serviceQueueRepo;
		this.customerQueueServiceUtility = customerQueueServiceUtility;
		this.serviceTypeForCounterRepo = serviceTypeForCounterRepo;
		this.serviceTypeRepo = serviceTypeRepo;

	}

	/**
	 * 
	 * @param serviceQueue
	 * @return
	 * @throws Exception
	 */
	public ServiceQueue requestNewToken(ServiceQueue serviceQueue) throws Exception {
		logger.debug("Im in service to generate new token");
		try {
			TokenNumberForDate tokenNumberForDate = customerQueueServiceUtility
					.getNewTokenForDate(serviceQueue.getQueueDate(), serviceQueue.getBranchCode());
			serviceQueue.setQueueDate(tokenNumberForDate.getTokenNumberForDateCK().getTokenDate());
			serviceQueue.setTokenNumber(tokenNumberForDate.getLatestTokenNumberIssued());
			serviceQueue.setBranchCode(tokenNumberForDate.getTokenNumberForDateCK().getBranchCode());
			serviceQueue.setCustomerQueueStatus(CustomerQueueStatus.PENDING);
			serviceQueue.setQueueTimeStamp(new Time(new Date().getTime()));
			serviceQueue.setCounterNumber(0L);
			logger.debug("Inside try block to save service queue");
			serviceQueueRepo.save(serviceQueue);
		} catch (Exception exception) {
			logger.error("Got error while persisting!", exception);
			throw new Exception(exception.getMessage());
		} 
		customerQueueServiceUtility.websocketPushForQueueDepth(serviceQueue);

		logger.debug("repo>>");
		return serviceQueue;

	}

	/**
	 * 
	 * @param serviceQueue
	 * @return
	 * @throws Exception
	 */

	public ServiceQueue allocateNextQueueItem(ServiceQueue serviceQueue) throws Exception {

		try {
			List<Long> serviceType = new ArrayList<Long>();
			List<ServiceTypeForCounter> serviceTypeForCounterData = serviceTypeForCounterRepo
					.findByCounterId(serviceQueue.getCounterNumber());
			if (!serviceTypeForCounterData.isEmpty()) {
				for (int i = 0; i < serviceTypeForCounterData.size(); i++) {
					ServiceTypeForCounter serviceTypeForCounterServiceTypeData = serviceTypeForCounterData.get(i);
					serviceType.add(serviceTypeForCounterServiceTypeData.getServiceTypeId());
				}

			} else {
				List<ServiceType> allServiceType = serviceTypeRepo.findAll();
				for (int i = 0; i < allServiceType.size(); i++) {
					ServiceType allServiceTypes = allServiceType.get(i);
					serviceType.add(allServiceTypes.getServiceTypeId());
				}
			}

			Optional<ServiceQueue> alreadyAllocatedToCounterdata = serviceQueueRepo
					.findFirstByCustomerQueueStatusAndBranchCodeAndQueueDateAndCounterNumber(
							CustomerQueueStatus.ALLOCATED, serviceQueue.getBranchCode(), serviceQueue.getQueueDate(),
							serviceQueue.getCounterNumber());
			if (alreadyAllocatedToCounterdata.isPresent()) {
				throw new Exception(ErrorCodes.E_COUNTER_BUSSY.toString());
			} else {
				Optional<ServiceQueue> pendingServiceQueueData = serviceQueueRepo
						.findFirstByCustomerQueueStatusAndBranchCodeAndQueueDateAndServiceTypeIdInOrderByQueueTimeStampAsc(
								CustomerQueueStatus.PENDING, serviceQueue.getBranchCode(), serviceQueue.getQueueDate(),
								serviceType);
				if (pendingServiceQueueData.isPresent()) {
					pendingServiceQueueData.get().setAllocationTimeStamp(new Timestamp(new Date().getTime()));
					pendingServiceQueueData.get().setCounterNumber(serviceQueue.getCounterNumber());
					pendingServiceQueueData.get().setAllocatedId(serviceQueue.getAllocatedId());
					pendingServiceQueueData.get().setCustomerQueueStatus(CustomerQueueStatus.ALLOCATED);

					logger.debug("Inside try block to save service queue");
					serviceQueueRepo.save(pendingServiceQueueData.get());

					customerQueueServiceUtility.websocketPushForQueueDepth(pendingServiceQueueData.get());
					customerQueueServiceUtility.websocketPushForTableData(pendingServiceQueueData.get());
					customerQueueServiceUtility.websocketPushForAnnouncement(pendingServiceQueueData.get());
					return pendingServiceQueueData.get();
				} else {
					throw new Exception(ErrorCodes.E_NO_SERVICE_QUEUE_DATA.toString());
				}

			}
		} catch (Exception exception) {
			logger.error("Got error while persisting!", exception);
			throw exception;
		}

	}


	@SuppressWarnings("deprecation")
	private ServiceQueue computeStatistics(ServiceQueue serviceQueue) throws Exception {
		logger.debug("Inside computeStatistics() to compute serviceCompletionTime or serviceRejectionTime in seconds");
		if (serviceQueue.getCustomerQueueStatus() == CustomerQueueStatus.SERVICED) {

			serviceQueue.setServiceCompletionTimeStamp(new Time(new Date().getTime()));
			int hr = serviceQueue.getServiceCompletionTimeStamp().getHours()
					- serviceQueue.getAllocationTimeStamp().getHours();
			int min = serviceQueue.getServiceCompletionTimeStamp().getMinutes()
					- serviceQueue.getAllocationTimeStamp().getMinutes();
			int sec = serviceQueue.getServiceCompletionTimeStamp().getSeconds()
					- serviceQueue.getAllocationTimeStamp().getSeconds();
			int servicedTimeInSec = sec + (60 * min) + (3600 * hr);
			serviceQueue.setServicedTimeInSec(servicedTimeInSec);
			try {
				logger.debug("Inside try block to save service queue");
				serviceQueueRepo.save(serviceQueue);

			} catch (Exception exception) {
				logger.error("Got error while persisting!", exception);
			}

			BranchServiceStatistics branchServiceStatistics = customerQueueServiceUtility
					.computingBranchServiceStatistics(serviceQueue);
			TellerServiceStatistics tellerServiceStatistics = customerQueueServiceUtility
					.computingTellerServiceStatistics(serviceQueue);
			System.out.println(tellerServiceStatistics);
			System.out.println(branchServiceStatistics);

			customerQueueServiceUtility.websocketPushForQueueDepth(serviceQueue);
			customerQueueServiceUtility.websocketPushForStatistics(serviceQueue);
			customerQueueServiceUtility.websocketPushForTableData(serviceQueue);
			return serviceQueue;
		} else {
			serviceQueue.setRejectionTimeStamp(new Timestamp(new Date().getTime()));
			int hr = serviceQueue.getRejectionTimeStamp().getHours() - serviceQueue.getQueueTimeStamp().getHours();
			int min = serviceQueue.getRejectionTimeStamp().getMinutes() - serviceQueue.getQueueTimeStamp().getMinutes();
			int sec = serviceQueue.getRejectionTimeStamp().getSeconds() - serviceQueue.getQueueTimeStamp().getSeconds();
			int rejectedTimeInSec = sec + (60 * min) + (3600 * hr);
			serviceQueue.setRejectedTimeInSec(rejectedTimeInSec);

			try {
				logger.debug("Inside try block to save service queue");
				serviceQueueRepo.save(serviceQueue);

			} catch (Exception exception) {
				logger.error("Got error while persisting!", exception);
			}
			BranchServiceStatistics branchServiceStatistics = customerQueueServiceUtility
					.computingBranchServiceStatistics(serviceQueue);
			TellerServiceStatistics tellerServiceStatistics = customerQueueServiceUtility
					.computingTellerServiceStatistics(serviceQueue);
			System.out.println(tellerServiceStatistics);
			System.out.println(branchServiceStatistics);
			// web socket push for queue depth
			customerQueueServiceUtility.websocketPushForQueueDepth(serviceQueue);
			customerQueueServiceUtility.websocketPushForTableData(serviceQueue);
			customerQueueServiceUtility.websocketPushForStatistics(serviceQueue);
			return serviceQueue;
		}
	}

	public ServiceQueue skipQueueItem(ServiceQueue serviceQueue) throws Exception {
		try {
			Optional<ServiceQueue> skipServiceQueueData = serviceQueueRepo.findByQueueDateAndBranchCodeAndTokenNumberAndCustomerQueueStatus(serviceQueue.getQueueDate(),
					serviceQueue.getBranchCode(), serviceQueue.getTokenNumber(),CustomerQueueStatus.ALLOCATED);
			if (skipServiceQueueData.isPresent()) {
				skipServiceQueueData.get().setCustomerQueueStatus(CustomerQueueStatus.SKIPPED);
				skipServiceQueueData.get().setAllocationTimeStamp(null);
				serviceQueueRepo.save(skipServiceQueueData.get());
				customerQueueServiceUtility.websocketPushForQueueDepth(serviceQueue);
				customerQueueServiceUtility.websocketPushForTableData(serviceQueue);
				customerQueueServiceUtility.websocketPushForStatistics(serviceQueue);
				return skipServiceQueueData.get();
			} else {
				throw new Exception(ErrorCodes.E_NO_DATA_TO_SKIP.toString());
			}
		} catch (Exception exception) {
			logger.error("Got error while persisting!", exception);
			throw exception;
		} 

	}

	public ServiceQueue recall(ServiceQueue serviceQueue) throws Exception {
		try {
			Optional<ServiceQueue> announcementData = serviceQueueRepo.findByQueueDateAndBranchCodeAndTokenNumberAndCustomerQueueStatus(serviceQueue.getQueueDate(),
					serviceQueue.getBranchCode(), serviceQueue.getTokenNumber(),CustomerQueueStatus.ALLOCATED);
			if (announcementData.isPresent()) {
				customerQueueServiceUtility.websocketPushForAnnouncement(serviceQueue);
				return announcementData.get();
			} else {
				throw new Exception(ErrorCodes.E_NO_DATA_TO_RECALL.toString());
			}

		} catch (Exception exception) {
			logger.error("Got error while persisting!", exception);
			throw exception;
		} 

	}

	public List<ServiceQueue> getQueueForDay(ServiceQueue serviceQueue) {
		List<ServiceQueue> queueForDay = serviceQueueRepo.findByQueueDateAndBranchCode(serviceQueue.getQueueDate(),
				serviceQueue.getBranchCode());
		return queueForDay;
	}

	public ServiceQueue markAsServiced(ServiceQueue serviceQueue) throws Exception {
		logger.debug("Im in side markAsServiced() to mark as Serviced");
		try {
			
			Optional<ServiceQueue> allocatedServiceQueueData = serviceQueueRepo.findByQueueDateAndBranchCodeAndTokenNumberAndCustomerQueueStatus(serviceQueue.getQueueDate(),
					serviceQueue.getBranchCode(), serviceQueue.getTokenNumber(),CustomerQueueStatus.ALLOCATED);
			if (allocatedServiceQueueData.isPresent()) {
				allocatedServiceQueueData.get().setCustomerQueueStatus(CustomerQueueStatus.SERVICED);
				serviceQueue = computeStatistics(allocatedServiceQueueData.get());
			} else {
				throw new Exception(ErrorCodes.E_NO_DATA_TO_SEVICE.toString());
			}
		} catch (Exception exception) {
			logger.error("Got error while persisting!", exception);
			throw exception;
		} 
		return serviceQueue;
	}
	

	public List<ServiceQueue> getSkippedQueueItem(ServiceQueue serviceQueue)throws Exception  {
		logger.debug("Im in side getSkippedQueueItem() to get skipped queue item");
		try {
			List<ServiceQueue> skippedServiceQueueData = serviceQueueRepo
					.findByQueueDateAndBranchCodeAndCustomerQueueStatusAndCounterNumber(serviceQueue.getQueueDate(),
							serviceQueue.getBranchCode(),CustomerQueueStatus.SKIPPED,serviceQueue.getCounterNumber());
			
				return skippedServiceQueueData;
			
		} catch (Exception exception) {
			logger.error("Got error while persisting!", exception);
			throw new Exception(exception.getMessage());
		}
		
	}

	public ServiceQueue recallItemInSkipQueue(ServiceQueue serviceQueue) throws Exception {
		logger.debug("Im in side recallItemInSkipQueue() to recall item in skipped queue");
		try {
			Optional<ServiceQueue> alreadyAllocatedToCounterdata = serviceQueueRepo
					.findFirstByCustomerQueueStatusAndBranchCodeAndQueueDateAndCounterNumber(
							CustomerQueueStatus.ALLOCATED, serviceQueue.getBranchCode(), serviceQueue.getQueueDate(),
							serviceQueue.getCounterNumber());
			if (alreadyAllocatedToCounterdata.isPresent()) {
				throw new Exception(ErrorCodes.E_COUNTER_BUSSY.toString());
			} else {
		Optional<ServiceQueue>skippedQueueItem=serviceQueueRepo.findByQueueDateAndBranchCodeAndTokenNumberAndCustomerQueueStatus(serviceQueue.getQueueDate(),
				serviceQueue.getBranchCode(), serviceQueue.getTokenNumber(),CustomerQueueStatus.SKIPPED);
		if(skippedQueueItem.isPresent()) {
			skippedQueueItem.get().setCustomerQueueStatus(CustomerQueueStatus.ALLOCATED);
			skippedQueueItem.get().setAllocationTimeStamp(new Timestamp(new Date().getTime()));
			
		}else {
			throw new Exception(ErrorCodes.E_NO_DATA_TO_SEVICE.toString());
		}
			
		serviceQueueRepo.save(skippedQueueItem.get());
		customerQueueServiceUtility.websocketPushForQueueDepth(serviceQueue);
		customerQueueServiceUtility.websocketPushForTableData(serviceQueue);
		customerQueueServiceUtility.websocketPushForStatistics(serviceQueue);
		return skippedQueueItem.get();
		}
		}catch (Exception exception) {
			logger.error("Got error while persisting!", exception);
			throw exception;
		} 
		
		
	}

	public Integer getTotalNumberServicedToday(ServiceQueue serviceQueue) {
		Integer totalNumberServicedToday=serviceQueueRepo.countByQueueDateAndBranchCodeAndCustomerQueueStatusAndCounterNumber(serviceQueue.getQueueDate(),
				serviceQueue.getBranchCode(), CustomerQueueStatus.SERVICED, serviceQueue.getCounterNumber());
		
		return totalNumberServicedToday;
	}
	
	public List<ServiceQueue> getTotallNumberServicedForCounter(ServiceQueue serviceQueue) {
		List<ServiceQueue> listOfServicedDataForCounter=serviceQueueRepo.findByQueueDateAndBranchCodeAndCounterNumberAndCustomerQueueStatus(serviceQueue.getQueueDate(),
				serviceQueue.getBranchCode(), serviceQueue.getCounterNumber(), CustomerQueueStatus.SERVICED);
		
		return listOfServicedDataForCounter;
	}

}