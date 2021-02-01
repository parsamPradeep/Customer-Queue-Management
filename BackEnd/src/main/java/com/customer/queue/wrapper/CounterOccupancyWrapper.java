package com.customer.queue.wrapper;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.customer.queue.codes.SuccessCodes;
import com.customer.queue.entities.Counter;
import com.customer.queue.entities.CounterOccupancy;
import com.customer.queue.model.RequestModel;
import com.customer.queue.model.ResponseModel;
import com.customer.queue.services.CounterOccupancyService;
import com.customer.queue.services.CustomerQueueServiceUtility;

@Component
public class CounterOccupancyWrapper {

	Logger log = LoggerFactory.getLogger(CustomerQueueWrapper.class);
	private CustomerQueueServiceUtility customerQueueServiceUtility;
	private CounterOccupancyService counterOccupancyService;

	@Autowired
	CounterOccupancyWrapper(CustomerQueueServiceUtility customerQueueServiceUtility,
			CounterOccupancyService counterOccupancyService) {
		this.counterOccupancyService = counterOccupancyService;
		this.customerQueueServiceUtility = customerQueueServiceUtility;
	}

	public ResponseModel getAvailableCounter(RequestModel requestModel) throws Exception {
		log.debug("i am in the com.customer.queue.wrapper to get all counters");
		log.debug(requestModel.toString());
		CounterOccupancy counterOccupancy = customerQueueServiceUtility.validateForCounterOccupancy(requestModel);
		List<Counter> avialableCounters = counterOccupancyService.getAvailableCounter(counterOccupancy);
		if (!avialableCounters.isEmpty())
			return this.customerQueueServiceUtility.generateSuccessResponse(requestModel, SuccessCodes.S_AVIALABLE_COUNTER_OBTAINED.toString());
		else
			return this.customerQueueServiceUtility.generateSuccessResponse(requestModel, SuccessCodes.S_NO_AVIALABLE_COUNTER_OBTAINED.toString());
	}

	public ResponseModel occupyCounter(RequestModel requestModel) throws Exception {
		log.debug("i am in the com.customer.queue.wrapper to occupy counter");
		log.debug(requestModel.toString());
		CounterOccupancy counterOccupancy=null;
		try {
			counterOccupancy = customerQueueServiceUtility.validateForOccupancy(requestModel);
			counterOccupancy = counterOccupancyService.occupyCounter(counterOccupancy);
			return this.customerQueueServiceUtility.generateSuccessResponse(requestModel, SuccessCodes.S_COUNTER_OCCUPIED.toString());
		} catch (Exception exception) {
			return this.customerQueueServiceUtility.generateErrorRespose(requestModel, exception);
		}
	}

	public ResponseModel releaseCounter(RequestModel requestModel) throws Exception {
		log.debug("i am in the com.customer.queue.wrapper to release counter");
		log.debug(requestModel.toString());
		CounterOccupancy counterOccupancy=null;
		try {
			counterOccupancy = customerQueueServiceUtility.validateForOccupancy(requestModel);
			counterOccupancy = counterOccupancyService.releaseCounter(counterOccupancy);
			return this.customerQueueServiceUtility.generateSuccessResponse(requestModel, SuccessCodes.S_COUNTER_RELEASED.toString());
		} catch (Exception exception) {
			return this.customerQueueServiceUtility.generateErrorRespose(requestModel, exception);
		}
	}

}
