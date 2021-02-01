package com.customer.queue.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.customer.queue.codes.ErrorCodes;
import com.customer.queue.entities.Counter;
import com.customer.queue.entities.ServiceType;
import com.customer.queue.entities.ServiceTypeForCounter;
import com.customer.queue.repos.CounterRepo;
import com.customer.queue.repos.ServiceTypeForCounterRepo;
import com.customer.queue.repos.ServiceTypeRepo;

@Transactional(rollbackFor = Exception.class)
@Service
public class StaticDataService {
	private Logger logger = LoggerFactory.getLogger(CustomerQueueService.class);
	private ServiceTypeRepo serviceTypeRepo;
	private CounterRepo counterRepo;
	private ServiceTypeForCounterRepo serviceTypeForCounterRepo;

	@Autowired
	public StaticDataService(ServiceTypeRepo serviceTypeRepo,
			CounterRepo counterRepo,ServiceTypeForCounterRepo serviceTypeForCounterRepo) {
		this.serviceTypeRepo = serviceTypeRepo;
		this.counterRepo=counterRepo;
		this.serviceTypeForCounterRepo=serviceTypeForCounterRepo;

	}


	public List<ServiceType> getAllServiceTypes() throws Exception {
		logger.debug("Im in service to get all service type");
		return serviceTypeRepo.findAll(); 
	}


	public List<ServiceType> removeServiceType(ServiceType serviceType) throws Exception {
		logger.debug("Im in service to remove service type");
		try {
			Optional<ServiceType> serviceTypeData= serviceTypeRepo.findByServiceTypeId(serviceType.getServiceTypeId());
			if(serviceTypeData.isPresent()) {
			List<ServiceType> deletedServiceTypeData=serviceTypeRepo.deleteByServiceTypeId(serviceType.getServiceTypeId());
				return deletedServiceTypeData;
			}else {
				throw new Exception(ErrorCodes.E_NO_SERVICE_TYPE_DATA.toString());
			}
		}catch (Exception exception) {
			logger.error("Got error while persisting!", exception);
			throw exception;
		}
	}


	public ServiceType addServiceType(ServiceType serviceType) throws Exception {
		logger.debug("Im in service to add service type");
		try{
			Optional<ServiceType>serviceTypeData=serviceTypeRepo.findByServiceTypeDescription(serviceType.getServiceTypeDescription());
			if(!serviceTypeData.isPresent()) {
			serviceTypeRepo.save(serviceType);
			return serviceType;
				}else {
			throw new Exception(ErrorCodes.E_SERVICE_TYPE_EXITS.toString());
				}
			
	}catch (Exception exception) {
		logger.error("Got error while persisting!", exception);
		throw exception;
	}
	

}


	public List<Counter> getAllCounters(Counter counter) throws Exception {
		logger.debug("Im in service to get all counters");
		
			return counterRepo.findByBranchCode(counter.getBranchCode());
	}


	public Counter removeCounter(Counter counter) throws Exception {
		logger.debug("Im in service to remove counter");
		try {
			Optional<Counter> counterData=counterRepo.findByCounterId(counter.getCounterId());
			if(counterData.isPresent()){
				List<Counter> deletedCounter=counterRepo.deleteByCounterId(counter.getCounterId());
				return deletedCounter.get(0);
		}else {
			throw new Exception(ErrorCodes.E_NO_COUNTER_DATA.toString());
		}
		}catch (Exception exception) {
			logger.error("Got error while persisting!", exception);
			throw exception;
		}
	}


	public Counter addCounter(Counter counter) throws Exception {
		logger.debug("Im in service to add counter");
		try{
			Optional<Counter>counterData=counterRepo.findByCounterDescription(counter.getCounterDescription());
			if(!counterData.isPresent()) {
				counterRepo.save(counter);
			return counter;
				}else {
			throw new Exception(ErrorCodes.E_COUNTER_EXITS.toString());
				}
			
	}catch (Exception exception) {
		logger.error("Got error while persisting!", exception);
		throw exception;
	}
	}


	public ServiceTypeForCounter addServiceTypeForCounter(ServiceTypeForCounter serviceTypeForCounter) throws Exception {
		logger.debug("Im in service to add ServiceType For Counter");
		
		try{
			Optional<Counter> counterData=counterRepo.findByCounterId(serviceTypeForCounter.getCounterId());
			if(!counterData.isPresent()) {
				throw new Exception(ErrorCodes.E_NO_SUCH_COUNTER_MAINTAINED.toString());
			}
			Optional<ServiceType> serviceTypeData= serviceTypeRepo.findByServiceTypeId(serviceTypeForCounter.getServiceTypeId());
			if(!serviceTypeData.isPresent()) {
				throw new Exception(ErrorCodes.E_NO_SUCH_SERVICE_TYPE_MAINTAINED.toString());
			}
			Optional<ServiceTypeForCounter>serviceTypeForCounterData=serviceTypeForCounterRepo.findByCounterIdAndServiceTypeId(serviceTypeForCounter.getCounterId(), serviceTypeForCounter.getServiceTypeId());
			if(!serviceTypeForCounterData.isPresent()) {
				serviceTypeForCounterRepo.save(serviceTypeForCounter);
			return serviceTypeForCounter;
				}else {
			throw new Exception(ErrorCodes.E_SERVICE_TYPE_ALREADY_MAPPED.toString());
				}
			
	}catch (Exception exception) {
		logger.error("Got error while persisting!", exception);
		throw exception;
	}
	}


	public ServiceTypeForCounter removeServiceTypeForCounter(ServiceTypeForCounter serviceTypeForCounter) throws Exception {
		logger.debug("Im in service to remove Service Type For Counter");
		try {
			Optional<ServiceTypeForCounter> serviceTypeForCounterData=serviceTypeForCounterRepo.findByCounterIdAndServiceTypeId(serviceTypeForCounter.getCounterId(), serviceTypeForCounter.getServiceTypeId());
			if(serviceTypeForCounterData.isPresent()){
				Optional<ServiceTypeForCounter> deletedserviceTypeForCounterData=serviceTypeForCounterRepo.deleteByCounterIdAndServiceTypeId(serviceTypeForCounter.getCounterId(), serviceTypeForCounter.getServiceTypeId());
				return deletedserviceTypeForCounterData.get();
		}else {
			throw new Exception(ErrorCodes.E_NO_SERVICE_TYPE_FOR_COUNTER_DATA.toString());
		}
		}catch (Exception exception) {
			logger.error("Got error while persisting!", exception);
			throw exception;
		}
		
	}


	public List<ServiceTypeForCounter> getAllServiceTypeForCounter() {
		logger.debug("Im in service to get all serviceTypeForCounter");
		 return serviceTypeForCounterRepo.findAll();
	}


	public List<ServiceTypeForCounter> getServiceTypeForCounter(ServiceTypeForCounter serviceTypeForCounter) {
		logger.debug("Im in service to get serviceTypeForCounter");
		List<ServiceTypeForCounter> serviceTypeForCounterData=serviceTypeForCounterRepo.findByCounterId(serviceTypeForCounter.getCounterId());
		return serviceTypeForCounterData;
	}

	
}
