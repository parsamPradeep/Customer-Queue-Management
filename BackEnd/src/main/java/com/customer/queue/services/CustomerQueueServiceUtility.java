package com.customer.queue.services;

import java.io.File;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.customer.queue.codes.ErrorCodes;
import com.customer.queue.codes.SuccessCodes;
import com.customer.queue.constants.CustomerQueueStatus;
import com.customer.queue.constants.WebSocketPushConstants;
import com.customer.queue.entities.Counter;
import com.customer.queue.entities.CounterOccupancy;
import com.customer.queue.entities.ServiceQueue;
import com.customer.queue.entities.ServiceType;
import com.customer.queue.entities.ServiceTypeForCounter;
import com.customer.queue.entities.TokenNumberForDate;
import com.customer.queue.entities.TokenNumberForDateCK;
import com.customer.queue.model.RequestModel;
import com.customer.queue.model.ResponseModel;
import com.customer.queue.model.ResponseStatus;
import com.customer.queue.repos.BranchServiceStatisticsRepo;
import com.customer.queue.repos.ServiceQueueRepo;
import com.customer.queue.repos.ServiceTypeRepo;
import com.customer.queue.repos.TellerServiceStatisticsRepo;
import com.customer.queue.repos.TokenNumberForDateRepo;
import com.customer.queue.statistics.BranchServiceStatistics;
import com.customer.queue.statistics.BranchServiceStatisticsCK;
import com.customer.queue.statistics.TellerServiceStatistics;
import com.customer.queue.statistics.TellerServiceStatisticsCK;
import com.customer.queue.ymlfactory.YamlPropertyLoaderFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@PropertySource(value = "file:${PROPERTY_PATH}", factory = YamlPropertyLoaderFactory.class)
@Transactional(rollbackFor = Exception.class)
public class CustomerQueueServiceUtility {
	Logger log = LoggerFactory.getLogger(CustomerQueueServiceUtility.class);
	@Value("${finflowz.campainmediaurl}")
	private String campainMediaUrl;
	@Value("${finflowz.campainmediafolder}")
	private String campainMediaFolder;

	private BranchServiceStatisticsRepo branchServiceStatisticsRepo;
	private TellerServiceStatisticsRepo tellerServiceStatisticsRepo;
	private ServiceQueueRepo serviceQueueRepo;
	private ServiceTypeRepo serviceTypeRepo;
	private TokenNumberForDateRepo tokenNumberForDateRepo;
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@Autowired
	public CustomerQueueServiceUtility(
			@Qualifier(value = "errorCodeSource") ResourceBundleMessageSource errorCodesDescriptionSource,
			@Qualifier(value = "successCodeSource") ResourceBundleMessageSource successCodesDescriptionSource,
			BranchServiceStatisticsRepo branchServiceStatisticsRepo,
			TellerServiceStatisticsRepo tellerServiceStatisticsRepo,
			ServiceQueueRepo serviceQueueRepo, ServiceTypeRepo serviceTypeRepo,
			TokenNumberForDateRepo tokenNumberForDateRepo) {

		this.branchServiceStatisticsRepo = branchServiceStatisticsRepo;
		this.tellerServiceStatisticsRepo = tellerServiceStatisticsRepo;
		this.serviceQueueRepo = serviceQueueRepo;
		this.serviceTypeRepo = serviceTypeRepo;
		this.tokenNumberForDateRepo = tokenNumberForDateRepo;
	}
	
	public ServiceQueue validateForRequestNewToken(RequestModel requestModel) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		ServiceQueue serviceQueue = mapper.convertValue(requestModel.getData(), ServiceQueue.class);
		serviceQueue.setQueueDate(requestModel.getApplicationDate());
		serviceQueue.setBranchCode(requestModel.getBranchCode());

		if (serviceQueue.getServiceTypeId() == null) {
			log.error("ServiceType must choose");
			throw new Exception(ErrorCodes.E_SERVICE_TYPE_MANDATORY.toString());
		}
		if (serviceQueue.getQueueDate() == null) {
			log.error("Date can't be blank");
			throw new Exception(ErrorCodes.E_DATE_MANDATORY.toString());
		}
		if (serviceQueue.getBranchCode() == null) {
			log.error("Branch code cbs can't be blank");
			throw new Exception(ErrorCodes.E_BRANCH_CODECBS_MANDATORY.toString());
		}
		return serviceQueue;
	}

	public ServiceQueue validateForAll(RequestModel requestModel) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		ServiceQueue serviceQueue = mapper.convertValue(requestModel.getData(), ServiceQueue.class);
		serviceQueue.setQueueDate(requestModel.getApplicationDate());
		serviceQueue.setBranchCode(requestModel.getBranchCode());

		if (serviceQueue.getAllocatedId() == null) {
			log.error("Teller Id can't  be blank");
			throw new Exception(ErrorCodes.E_TELLER_ID_MANDATORY.toString());
		}
		if (serviceQueue.getBranchCode() == null) {
			log.error("branch Code Cbs can't  be blank");
			throw new Exception(ErrorCodes.E_BRANCH_CODECBS_MANDATORY.toString());
		}
		if (serviceQueue.getCounterNumber() == null) {
			log.error("Counter Number name can't  be blank");
			throw new Exception(ErrorCodes.E_COUNTER_NUMBER_MANDATORY.toString());

		}
		if (serviceQueue.getQueueDate() == null) {
			log.error("Date can't be blank");
			throw new Exception(ErrorCodes.E_DATE_MANDATORY.toString());
		}
		return serviceQueue;
	}

	public ServiceQueue validateForgetQueueForDayAndGetSkippedToken(RequestModel requestModel) throws Exception {
		ServiceQueue serviceQueue = new ServiceQueue();
		ObjectMapper mapper = new ObjectMapper();
		serviceQueue = mapper.convertValue(requestModel.getData(), ServiceQueue.class);
		serviceQueue.setQueueDate(requestModel.getApplicationDate());
		serviceQueue.setBranchCode(requestModel.getBranchCode());
		
		if (serviceQueue.getBranchCode() == null) {
			log.error("branch Code Cbs can't  be blank");
			throw new Exception(ErrorCodes.E_BRANCH_CODECBS_MANDATORY.toString());
		}
		if (serviceQueue.getQueueDate() == null) {
			log.error("Date can't be blank");
			throw new Exception(ErrorCodes.E_DATE_MANDATORY.toString());
		}

		return serviceQueue;
	}

	public ServiceType validateForRemoveServiceType(RequestModel RequestModel) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		ServiceType serviceType = mapper.convertValue(RequestModel.getData(), ServiceType.class);

		if (serviceType.getServiceTypeId() == 0) {
			log.error("Service Type Id can't  be blank");
			throw new Exception(ErrorCodes.E_SERVICE_TYPE_ID_MANDATORY.toString()
					);
		}
		return serviceType;
	}
	
	public ServiceType validateForGetServiceTypeForBranch(RequestModel RequestModel) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		ServiceType serviceType = mapper.convertValue(RequestModel.getData(), ServiceType.class);
		if (serviceType.getBranchCode() == null) {
			log.error("Branch code cbs can't  be blank");
			throw new Exception(ErrorCodes.E_BRANCH_CODECBS_MANDATORY.toString()
					);
		}
		return serviceType;
	}

	public ServiceType validateForAddServiceType(RequestModel RequestModel) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		ServiceType serviceType = mapper.convertValue(RequestModel.getData(), ServiceType.class);
		if (serviceType.getServiceTypeDescription() == null) {
			log.error("Branch code cbs can't  be blank");
			throw new Exception(ErrorCodes.E_SERVICE_TYPE_DESCRIPTION_MANDATORY.toString()
					);
		}

		if (serviceType.getServiceTypeMnemonic() == null) {
			log.error("Branch code cbs can't  be blank");
			throw new Exception(ErrorCodes.E_SERVICE_TYPE_MNEMONIC_MANDATORY.toString()
					);
		}
		return serviceType;
	}

	public Counter validateForGetAllCounters(RequestModel requestModel) throws Exception {
		Counter counter = new Counter();
		counter.setBranchCode(requestModel.getBranchCode());
		if (counter.getBranchCode() == null) {
			log.error("Branch code cbs can't  be blank");
			throw new Exception(ErrorCodes.E_BRANCH_CODECBS_MANDATORY.toString()
					);
		}

		return counter;
	}

	public Counter validateForRemoveCounter(RequestModel requestModel) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Counter counter = mapper.convertValue(requestModel.getData(), Counter.class);

		if (counter.getCounterId() == null) {
			log.error("Counter Id can't  be blank");
			throw new Exception(ErrorCodes.E_COUNTERID_MANDATORY.toString()
					);
		}
		return counter;
	}

	public Counter validateForAddingCounter(RequestModel requestModel) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Counter counter = mapper.convertValue(requestModel.getData(), Counter.class);
		counter.setBranchCode(requestModel.getBranchCode());
		if (counter.getBranchCode() == null) {
			log.error("Counter Id can't  be blank");
			throw new Exception(ErrorCodes.E_BRANCH_CODECBS_MANDATORY.toString()
					);
		}
		if (counter.getCounterDescription() == null) {
			log.error("Counter Id can't  be blank");
			throw new Exception(ErrorCodes.E_COUNTER_DESCRIPTION_MANDATORY.toString()
					);
		}

		return counter;
	}
	public ServiceQueue validateForMarkAsServiced(RequestModel requestModel) throws Exception {
		ServiceQueue serviceQueue = new ServiceQueue();
		ObjectMapper mapper = new ObjectMapper();
		serviceQueue = mapper.convertValue(requestModel.getData(), ServiceQueue.class);
		serviceQueue.setQueueDate(requestModel.getApplicationDate());
		serviceQueue.setBranchCode(requestModel.getBranchCode());
		//serviceQueue.setAllocatedTellerId(requestModel.getUserId());
		
		if (serviceQueue.getBranchCode() == null) {
			log.error("Counter Id can't  be blank");
			throw new Exception(ErrorCodes.E_BRANCH_CODECBS_MANDATORY.toString()
					);
		}
		if (serviceQueue.getQueueDate() == null) {
			log.error("Date can't be blank");
			throw new Exception(ErrorCodes.E_DATE_MANDATORY.toString()
					);
		}
		if (serviceQueue.getTokenNumber() == null) {
			log.error("Token number can't be blank");
			throw new Exception(ErrorCodes.E_TOKEN_NUMBER_MANDATORY.toString()
					);
		}
		if(serviceQueue.getCounterNumber()==null) {
			log.error("Counter number can't be blank");
			throw new Exception(ErrorCodes.E_COUNTER_NUMBER_MANDATORY.toString()
					);
		}
		if(serviceQueue.getAllocatedId()==null) {
			log.error("Counter number can't be blank");
			throw new Exception(ErrorCodes.E_TELLER_ID_MANDATORY.toString()
					);
		}
		return serviceQueue;
	}
	
	
	public ServiceTypeForCounter validateForAddServiceTypeForCounter(RequestModel RequestModel) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		ServiceTypeForCounter serviceTypeForCounter = mapper.convertValue(RequestModel.getData(), ServiceTypeForCounter.class);
		if(serviceTypeForCounter.getCounterId()==null) {
			log.error("CounterId can't be blank");
			throw new Exception(ErrorCodes.E_COUNTERID_MANDATORY.toString()
					);
		}
		if (serviceTypeForCounter.getServiceTypeId() == 0) {
			log.error("Service Type Id can't  be blank");
			throw new Exception(ErrorCodes.E_SERVICE_TYPE_ID_MANDATORY.toString()
					);
		}
		
		return serviceTypeForCounter;
	}
	public ServiceTypeForCounter validateForgetServiceTypeForCounter(RequestModel RequestModel) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		ServiceTypeForCounter serviceTypeForCounter = mapper.convertValue(RequestModel.getData(), ServiceTypeForCounter.class);
		if(serviceTypeForCounter.getCounterId()==null) {
			log.error("CounterId can't be blank");
			throw new Exception(ErrorCodes.E_COUNTERID_MANDATORY.toString()
					);
		}
		return serviceTypeForCounter;
	}
	public CounterOccupancy validateForCounterOccupancy(RequestModel RequestModel) throws Exception {
		CounterOccupancy counterOccupancy=new CounterOccupancy();
		counterOccupancy.setApplicationDate(RequestModel.getApplicationDate());
		counterOccupancy.setBranchCode(RequestModel.getBranchCode());
		//counterOccupancy.setAllocatedTellerId(RequestModel.getUserId());
		if(counterOccupancy.getBranchCode()==null) {
			log.error("CounterId can't be blank");
			throw new Exception(ErrorCodes.E_BRANCH_CODECBS_MANDATORY.toString()
					);
		}
		if(counterOccupancy.getAllocatedId()==null) {
			log.error("CounterId can't be blank");
			throw new Exception(ErrorCodes.E_ALLOCATED_TELLERID_MANDATORY.toString()
					);
		}
		if(counterOccupancy.getApplicationDate()==null) {
			log.error("CounterId can't be blank");
			throw new Exception(ErrorCodes.E_APPLICATION_DATE_MANDATORY.toString()
					);
		}
		return counterOccupancy;
	}


public CounterOccupancy validateForOccupancy(RequestModel RequestModel) throws Exception {
	CounterOccupancy counterOccupancy=new CounterOccupancy();
	ObjectMapper mapper = new ObjectMapper();
	counterOccupancy = mapper.convertValue(RequestModel.getData(), CounterOccupancy.class);
	//counterOccupancy.setAllocatedTellerId(RequestModel.getUserId());
	counterOccupancy.setApplicationDate(RequestModel.getApplicationDate());
	counterOccupancy.setBranchCode(RequestModel.getBranchCode());
	if(counterOccupancy.getBranchCode()==null) {
		log.error("CounterId can't be blank");
		throw new Exception(ErrorCodes.E_BRANCH_CODECBS_MANDATORY.toString()
				);
	}
	if(counterOccupancy.getAllocatedId()==null) {
		log.error("CounterId can't be blank");
		throw new Exception(ErrorCodes.E_ALLOCATED_TELLERID_MANDATORY.toString()
				);
	}
	if(counterOccupancy.getApplicationDate()==null) {
		log.error("CounterId can't be blank");
		throw new Exception(ErrorCodes.E_APPLICATION_DATE_MANDATORY.toString()
				);
	}
	if(counterOccupancy.getCounterNumber()==null) {
		log.error("CounterId can't be blank");
		throw new Exception(ErrorCodes.E_COUNTER_NUMBER_MANDATORY.toString()
				);
	}
	
	return counterOccupancy;
}

	public ResponseModel generateSuccessResponse(Object obj, ServiceQueue serviceQueue) {
		ResponseModel responseModel=new ResponseModel();
		String successCodes = gettingSuccessCode(serviceQueue);		
		responseModel.setData(serviceQueue);
		responseModel.setSuccessDetails(successCodes);
		responseModel.setResponseStatus(ResponseStatus.SUCCESS);
		return responseModel;
	}
	public ResponseModel generateSuccessResponse(Object obj,String successCode) {
		ResponseModel responseModel=new ResponseModel();	
		responseModel.setData(obj);
		responseModel.setSuccessDetails(successCode);
		responseModel.setResponseStatus(ResponseStatus.SUCCESS);
		return responseModel;
	}
	
	
	
	
	private String gettingSuccessCode(ServiceQueue serviceQueue) {

		if (serviceQueue.getCustomerQueueStatus() == CustomerQueueStatus.ALLOCATED) {
			return SuccessCodes.S_ALLOCATED_NEXT_QUEUE_ITEM.toString();
		} else if (serviceQueue.getCustomerQueueStatus() == CustomerQueueStatus.SERVICED) {
			return SuccessCodes.S_SERVICED_QUEUE_ITEM.toString();
		} else if (serviceQueue.getCustomerQueueStatus() == CustomerQueueStatus.REJECTED) {
			return SuccessCodes.S_REJECTED_QUEUE_ITEM.toString();
		} else {
			return SuccessCodes.S_TOKEN_NUM_GENERATED.toString();
		}

	}

	

	public ResponseModel generateErrorRespose(RequestModel RequestModel, Exception Exception) {
		ResponseModel responseModel=new ResponseModel();
		responseModel.setResponseStatus(ResponseStatus.ERROR);
		responseModel.setData(RequestModel);
		responseModel.setErrorDetails(Exception.getMessage());
		responseModel.setResponseStatus( ResponseStatus.ERROR);
		return responseModel;

	}
	


	public TokenNumberForDate getNewTokenForDate(Date branchDate, String branchCode) throws Exception {

		try {
			TokenNumberForDateCK tokenNumberForDateCK1 = new TokenNumberForDateCK();
			tokenNumberForDateCK1.setBranchCode(branchCode);
			tokenNumberForDateCK1.setTokenDate(branchDate);
			Optional<TokenNumberForDate> tokenNumberForDateData = tokenNumberForDateRepo
					.findByTokenNumberForDateCK(tokenNumberForDateCK1);
			if (tokenNumberForDateData.isPresent()) {
				tokenNumberForDateData.get()
						.setLatestTokenNumberIssued(tokenNumberForDateData.get().getLatestTokenNumberIssued() + 1);
				tokenNumberForDateRepo.save(tokenNumberForDateData.get());
				return tokenNumberForDateData.get();
			} else {
				TokenNumberForDate tokenNumberForDate = new TokenNumberForDate();
				TokenNumberForDateCK tokenNumberForDateCK = new TokenNumberForDateCK();
				tokenNumberForDateCK.setTokenDate(branchDate);
				tokenNumberForDateCK.setBranchCode(branchCode);
				tokenNumberForDate.setTokenNumberForDateCK(tokenNumberForDateCK);
				tokenNumberForDate.setLatestTokenNumberIssued(1);
				tokenNumberForDateRepo.save(tokenNumberForDate);
				return tokenNumberForDate;
			}
		} catch (Exception exception) {
			log.error("Got error while persisting!", exception);
			throw new Exception(exception.getMessage());
		}

	}

	public BranchServiceStatistics computingBranchServiceStatistics(ServiceQueue serviceQueue) throws Exception {
		log.debug("Inside computingBranchServiceStatistics() to save or update BranchServiceStatistics");
		BranchServiceStatistics branchServiceStatisticsDetails = settingBranchServiceStatisticsCK(serviceQueue);

		if (serviceQueue.getCustomerQueueStatus() == CustomerQueueStatus.SERVICED) {
			try {
				Integer cout = serviceQueueRepo.countByQueueDateAndBranchCodeAndCustomerQueueStatusAndServiceTypeId(
						serviceQueue.getQueueDate(), serviceQueue.getBranchCode(), CustomerQueueStatus.SERVICED,
						serviceQueue.getServiceTypeId());
				Optional<ServiceQueue> highestServiceTimeInSeconds = serviceQueueRepo
						.findFirstByQueueDateAndBranchCodeAndCustomerQueueStatusAndServiceTypeIdOrderByServicedTimeInSecDesc(
								serviceQueue.getQueueDate(), serviceQueue.getBranchCode(),
								CustomerQueueStatus.SERVICED, serviceQueue.getServiceTypeId());
				Optional<ServiceQueue> lowestServiceTimeInSeconds = serviceQueueRepo
						.findFirstByQueueDateAndBranchCodeAndCustomerQueueStatusAndServiceTypeIdOrderByServicedTimeInSecAsc(
								serviceQueue.getQueueDate(), serviceQueue.getBranchCode(),
								CustomerQueueStatus.SERVICED, serviceQueue.getServiceTypeId());

				if (branchServiceStatisticsDetails.getAverageServiceCompletionTimeInSeconds() == null)
					branchServiceStatisticsDetails.setAverageServiceCompletionTimeInSeconds(0);
				Integer averageServiceCompletionTimeInSeconds = ((branchServiceStatisticsDetails
						.getAverageServiceCompletionTimeInSeconds() * cout) + (serviceQueue.getServicedTimeInSec()))
						/ cout;
				branchServiceStatisticsDetails
						.setAverageServiceCompletionTimeInSeconds(averageServiceCompletionTimeInSeconds);
				branchServiceStatisticsDetails.setNumberServiced(cout);
				branchServiceStatisticsDetails
						.setHighestServiceTimeInSeconds(highestServiceTimeInSeconds.get().getServicedTimeInSec());
				branchServiceStatisticsDetails
						.setLowestServiceTimeInSeconds(lowestServiceTimeInSeconds.get().getServicedTimeInSec());

				branchServiceStatisticsRepo.save(branchServiceStatisticsDetails);
			} catch (Exception exception) {
				log.error("Got error while persisting!", exception);
			}
			return branchServiceStatisticsDetails;
		} else {
			try {
				Integer count = serviceQueueRepo.countByQueueDateAndBranchCodeAndCustomerQueueStatusAndServiceTypeId(
						serviceQueue.getQueueDate(), serviceQueue.getBranchCode(), CustomerQueueStatus.REJECTED,
						serviceQueue.getServiceTypeId());

				if (branchServiceStatisticsDetails.getAverageServiceRejectionTimeInSeconds() == null)
					branchServiceStatisticsDetails.setAverageServiceRejectionTimeInSeconds(0);
				Integer averageServiceRejectionTimeInSeconds = ((branchServiceStatisticsDetails
						.getAverageServiceRejectionTimeInSeconds() * count) + (serviceQueue.getRejectedTimeInSec()))
						/ count;
				branchServiceStatisticsDetails
						.setAverageServiceRejectionTimeInSeconds(averageServiceRejectionTimeInSeconds);
				branchServiceStatisticsDetails.setNumberRejected(count);

				branchServiceStatisticsRepo.save(branchServiceStatisticsDetails);
			} catch (Exception exception) {
				log.error("Got error while persisting!", exception);
			}
			return branchServiceStatisticsDetails;
		}

	}

	private BranchServiceStatistics settingBranchServiceStatisticsCK(ServiceQueue serviceQueue) {
		log.debug("Inside settingBranchServiceStatisticsCK() to save or updating BranchServiceStatisticsCK");
		BranchServiceStatisticsCK branchServiceStatisticsCK = new BranchServiceStatisticsCK();
		branchServiceStatisticsCK.setBranchCode(serviceQueue.getBranchCode());
		branchServiceStatisticsCK.setBranchDate(serviceQueue.getQueueDate());
		branchServiceStatisticsCK.setServiceTypeId(serviceQueue.getServiceTypeId());

		Optional<BranchServiceStatistics> branchServiceStatisticsDetails = branchServiceStatisticsRepo
				.findByBranchServiceStatisticsCK(branchServiceStatisticsCK);

		if (!branchServiceStatisticsDetails.isPresent()) {
			BranchServiceStatistics branchServiceStatistics = new BranchServiceStatistics();
			branchServiceStatistics.setBranchServiceStatisticsCK(branchServiceStatisticsCK);
			try {
				branchServiceStatisticsRepo.save(branchServiceStatistics);
			} catch (Exception exception) {
				log.error("Got error while persisting!", exception);
			}
			return branchServiceStatistics;
		}
		return branchServiceStatisticsDetails.get();
	}

	public TellerServiceStatistics computingTellerServiceStatistics(ServiceQueue serviceQueue) throws Exception {
		log.debug("Inside computingTellerServiceStatistics() to save or update TellerServiceStatistics");
		TellerServiceStatistics tellerServiceStatisticsDetails = settingTellerServiceStatisticsCK(serviceQueue);

		if (serviceQueue.getCustomerQueueStatus() == CustomerQueueStatus.SERVICED) {
			try {
				Integer cout = serviceQueueRepo
						.countByQueueDateAndBranchCodeAndCustomerQueueStatusAndAllocatedIdAndServiceTypeId(
								serviceQueue.getQueueDate(), serviceQueue.getBranchCode(),
								CustomerQueueStatus.SERVICED, serviceQueue.getAllocatedId(),
								serviceQueue.getServiceTypeId());
				Optional<ServiceQueue> highestServiceTimeInSeconds = serviceQueueRepo
						.findFirstByQueueDateAndBranchCodeAndCustomerQueueStatusAndServiceTypeIdAndAndAllocatedIdOrderByServicedTimeInSecDesc(
								serviceQueue.getQueueDate(), serviceQueue.getBranchCode(),
								CustomerQueueStatus.SERVICED, serviceQueue.getServiceTypeId(),
								serviceQueue.getAllocatedId());
				Optional<ServiceQueue> lowestServiceTimeInSeconds = serviceQueueRepo
						.findFirstByQueueDateAndBranchCodeAndCustomerQueueStatusAndServiceTypeIdAndAndAllocatedIdOrderByServicedTimeInSecAsc(
								serviceQueue.getQueueDate(), serviceQueue.getBranchCode(),
								CustomerQueueStatus.SERVICED, serviceQueue.getServiceTypeId(),
								serviceQueue.getAllocatedId());
				if (tellerServiceStatisticsDetails.getAverageServiceCompletionTimeInSeconds() == null)
					tellerServiceStatisticsDetails.setAverageServiceCompletionTimeInSeconds(0);
				Integer averageServiceCompletionTimeInSeconds = ((tellerServiceStatisticsDetails
						.getAverageServiceCompletionTimeInSeconds() * cout) + (serviceQueue.getServicedTimeInSec()))
						/ cout;
				tellerServiceStatisticsDetails
						.setAverageServiceCompletionTimeInSeconds(averageServiceCompletionTimeInSeconds);
				tellerServiceStatisticsDetails.setNumberServiced(cout);
				tellerServiceStatisticsDetails
						.setHighestServiceTimeInSeconds(highestServiceTimeInSeconds.get().getServicedTimeInSec());
				tellerServiceStatisticsDetails
						.setLowestServiceTimeInSeconds(lowestServiceTimeInSeconds.get().getServicedTimeInSec());

				tellerServiceStatisticsRepo.save(tellerServiceStatisticsDetails);
			} catch (Exception exception) {
				log.error("Got error while persisting!", exception);
			}
			return tellerServiceStatisticsDetails;

		} else {
			try {
				Integer count = serviceQueueRepo
						.countByQueueDateAndBranchCodeAndCustomerQueueStatusAndAllocatedIdAndServiceTypeId(
								serviceQueue.getQueueDate(), serviceQueue.getBranchCode(),
								CustomerQueueStatus.REJECTED, serviceQueue.getAllocatedId(),
								serviceQueue.getServiceTypeId());

				if (tellerServiceStatisticsDetails.getAverageServiceRejectionTimeInSeconds() == null)
					tellerServiceStatisticsDetails.setAverageServiceRejectionTimeInSeconds(0);
				Integer averageServiceRejectionTimeInSeconds = ((tellerServiceStatisticsDetails
						.getAverageServiceRejectionTimeInSeconds() * count) + (serviceQueue.getRejectedTimeInSec()))
						/ count;
				tellerServiceStatisticsDetails
						.setAverageServiceRejectionTimeInSeconds(averageServiceRejectionTimeInSeconds);
				tellerServiceStatisticsDetails.setNumberRejected(count);

				tellerServiceStatisticsRepo.save(tellerServiceStatisticsDetails);
			} catch (Exception exception) {
				log.error("Got error while persisting!", exception);
			}
			return tellerServiceStatisticsDetails;
		}

	}

	private TellerServiceStatistics settingTellerServiceStatisticsCK(ServiceQueue serviceQueue) {
		TellerServiceStatisticsCK tellerServiceStatisticsCK = new TellerServiceStatisticsCK();
		tellerServiceStatisticsCK.setBranchCode(serviceQueue.getBranchCode());
		tellerServiceStatisticsCK.setBranchDate(serviceQueue.getQueueDate());
		tellerServiceStatisticsCK.setServiceTypeId(serviceQueue.getServiceTypeId());
		tellerServiceStatisticsCK.setTellerId(serviceQueue.getAllocatedId());
		Optional<TellerServiceStatistics> tellerServiceStatisticsDetails = tellerServiceStatisticsRepo
				.findByTellerServiceStatisticsCK(tellerServiceStatisticsCK);

		if (!tellerServiceStatisticsDetails.isPresent()) {
			TellerServiceStatistics tellerServiceStatistics = new TellerServiceStatistics();
			tellerServiceStatistics.setTellerServiceStatisticsCK(tellerServiceStatisticsCK);
			try {
				tellerServiceStatisticsRepo.save(tellerServiceStatistics);
			} catch (Exception exception) {
				log.error("Got error while persisting!", exception);
			}
			return tellerServiceStatistics;
		}
		return tellerServiceStatisticsDetails.get();
	}

	public void websocketPushForQueueDepth(ServiceQueue serviceQueue) {
		log.debug("Inside websocketPushForQueueDepth() for websocketpush");
		try {
			List<CustomerQueueStatus> customerQueueStatus=new ArrayList<CustomerQueueStatus>();
			customerQueueStatus.add(CustomerQueueStatus.PENDING);
			customerQueueStatus.add(CustomerQueueStatus.SKIPPED);
			List<ServiceType> serviceTypeData = serviceTypeRepo.findAll();
			HashMap<String, Integer> queuedepth = new HashMap<>();
			for (int i = 0; i < serviceTypeData.size(); i++) {
				Integer queueDepthForServiceId = serviceQueueRepo
						.countByQueueDateAndBranchCodeAndServiceTypeIdAndCustomerQueueStatusIn(
								serviceQueue.getQueueDate(), serviceQueue.getBranchCode(), 
								serviceTypeData.get(i).getServiceTypeId(), customerQueueStatus);
				queuedepth.put(serviceTypeData.get(i).getServiceTypeDescription(), queueDepthForServiceId);
			}
			simpMessagingTemplate.convertAndSend(
					WebSocketPushConstants.QueueDepthTopic + "-" + serviceQueue.getBranchCode(), queuedepth);
		} catch (Exception exception) {
			log.error("Got error while websocketpush!", exception);
		}

	}

	public void websocketPushForStatistics(ServiceQueue serviceQueue) {
		log.debug("Inside websocketPushForStatistics() for websocketpush");
		try {
			List<ServiceType> serviceTypeData = serviceTypeRepo.findAll();
			HashMap<String, Integer> staticsticsForServiceType = new HashMap<>();
			for (int i = 0; i < serviceTypeData.size(); i++) {
				Integer numberofserviced = serviceQueueRepo
						.countByQueueDateAndBranchCodeAndCustomerQueueStatusAndServiceTypeId(
								serviceQueue.getQueueDate(), serviceQueue.getBranchCode(),
								CustomerQueueStatus.SERVICED, serviceTypeData.get(i).getServiceTypeId());

				BranchServiceStatisticsCK branchServiceStatisticsCK = new BranchServiceStatisticsCK();
				branchServiceStatisticsCK.setBranchCode(serviceQueue.getBranchCode());
				branchServiceStatisticsCK.setBranchDate(serviceQueue.getQueueDate());
				branchServiceStatisticsCK.setServiceTypeId(serviceTypeData.get(i).getServiceTypeId());
				Optional<BranchServiceStatistics> avgServiceTime = branchServiceStatisticsRepo
						.findByBranchServiceStatisticsCK(branchServiceStatisticsCK);
				staticsticsForServiceType.put(serviceTypeData.get(i).getServiceTypeDescription()
						+ WebSocketPushConstants.TotalNumberServicedKey, numberofserviced);

				if (!avgServiceTime.isPresent())
					staticsticsForServiceType.put(serviceTypeData.get(i).getServiceTypeDescription()
							+ WebSocketPushConstants.AverageServiceCompletionTimeKey, 0);
				else
					staticsticsForServiceType.put(
							serviceTypeData.get(i).getServiceTypeDescription()
									+ WebSocketPushConstants.AverageServiceCompletionTimeKey,
							avgServiceTime.get().getAverageServiceCompletionTimeInSeconds());
			}
			simpMessagingTemplate.convertAndSend(
					WebSocketPushConstants.StatisticsTopic + "-" + serviceQueue.getBranchCode(),
					staticsticsForServiceType);
		} catch (Exception exception) {
			log.error("Got error while websocketpush!", exception);
		}

	}

	public void websocketPushForTableData(ServiceQueue serviceQueue) {
		try {
			List<ServiceQueue> tableData = serviceQueueRepo.findByQueueDateAndBranchCodeAndCustomerQueueStatus(
					serviceQueue.getQueueDate(), serviceQueue.getBranchCode(), CustomerQueueStatus.ALLOCATED);
			simpMessagingTemplate.convertAndSend(
					WebSocketPushConstants.TableData + "-" + serviceQueue.getBranchCode(), tableData);
		} catch (Exception exception) {
			log.error("Got error while websocketpush!", exception);
		}

	}

	public void websocketPushForAnnouncement(ServiceQueue serviceQueue) {
		try {
			Optional<ServiceQueue> announcementData = serviceQueueRepo
					.findByQueueDateAndCounterNumberAndCustomerQueueStatusAndBranchCode(serviceQueue.getQueueDate(),
							serviceQueue.getCounterNumber(), CustomerQueueStatus.ALLOCATED,
							serviceQueue.getBranchCode());
			simpMessagingTemplate.convertAndSend(
					WebSocketPushConstants.AnnouncementTopic + "-" + serviceQueue.getBranchCode(), announcementData);
		} catch (Exception exception) {
			log.error("Got error while websocketpush!", exception);
		}

	}



	public String[] getCampainURLForBranch(RequestModel requestModel) {
		
		String  folderNameToAddUrl="";
		log.debug("campainMediaFolder-> "+campainMediaFolder);
		log.debug("campainMediaurl-> "+campainMediaUrl);
		if(this.campainMediaFolder.contains("/")) {
			folderNameToAddUrl=this.campainMediaFolder.split("/")[this.campainMediaFolder.split("/").length-1];
		}else {
			folderNameToAddUrl=this.campainMediaFolder.split(Pattern.quote(FileSystems.getDefault().getSeparator()))[this.campainMediaFolder.split(Pattern.quote(FileSystems.getDefault().getSeparator())).length-1];
		}
		log.debug("folderNameToAddUrl-> "+folderNameToAddUrl);
		log.debug("branch code-> "+requestModel.getBranchCode());
				File file = new File(this.campainMediaFolder+requestModel.getBranchCode());

		 String[] listOfFileNames = new String[file.list().length];
	        for(int i=0;i<file.list().length;i++){
	        	
	        	listOfFileNames[i]=this.campainMediaUrl+folderNameToAddUrl+"/"+requestModel.getBranchCode()+"/"+file.list()[i];
	        	
	        }
		return listOfFileNames;
	}



	
	

	
	

	

	

}
