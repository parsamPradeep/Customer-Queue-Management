package com.customer.queue.wrapper;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.customer.queue.codes.SuccessCodes;
import com.customer.queue.model.RequestModel;
import com.customer.queue.model.ResponseModel;
import com.customer.queue.entities.ServiceQueue;
import com.customer.queue.services.CustomerQueueService;
import com.customer.queue.services.CustomerQueueServiceUtility;

@Component
public class CustomerQueueWrapper {
	Logger log= LoggerFactory.getLogger(CustomerQueueWrapper.class);
	private CustomerQueueService customerQueueService;
	private CustomerQueueServiceUtility customerQueueServiceUtility;

	@Autowired
	CustomerQueueWrapper(CustomerQueueService customerQueueService, CustomerQueueServiceUtility customerQueueServiceUtility) {
		super();
		this.customerQueueService = customerQueueService;
		this.customerQueueServiceUtility=customerQueueServiceUtility;

	}

	public ResponseModel requestNewToken(RequestModel requestModel) throws Exception {
		log.debug("i am in the CustomerQueueWrapper.requestNewToken to request new token");
		log.debug(requestModel.toString());
		ServiceQueue serviceQueue=null;
		try{	serviceQueue=customerQueueServiceUtility.validateForRequestNewToken(requestModel);
			serviceQueue=customerQueueService.requestNewToken(serviceQueue);
			return this.customerQueueServiceUtility.generateSuccessResponse(requestModel,serviceQueue);
		}catch(Exception exception) {
			return this.customerQueueServiceUtility.generateErrorRespose(requestModel,exception);
		}

	}
	public ResponseModel allocateNextQueueItem(RequestModel requestModel) throws Exception {
		log.debug("i am in the CustomerQueueWrapper.allocateNextQueueItem to allocate next queue item");
		log.debug(requestModel.toString());
		ServiceQueue serviceQueue=null;
		try{
			serviceQueue=customerQueueServiceUtility.validateForAll(requestModel);
			serviceQueue=customerQueueService.allocateNextQueueItem(serviceQueue);
			return this.customerQueueServiceUtility.generateSuccessResponse(requestModel,serviceQueue);

		}catch(Exception exception) {
			log.debug("error data => :"+exception);
			return this.customerQueueServiceUtility.generateErrorRespose(requestModel,exception);
		}
	}
	public ResponseModel skipQueueItem(RequestModel requestModel) throws Exception {
		log.debug("i am in the com.customer.queue.wrapper to skip queue item");
		log.debug(requestModel.toString());
		ServiceQueue serviceQueue=null;
		try{
			serviceQueue=customerQueueServiceUtility.validateForAll(requestModel);
			serviceQueue=customerQueueService.skipQueueItem(serviceQueue);
			return this.customerQueueServiceUtility.generateSuccessResponse(requestModel,SuccessCodes.S_SKIPED_QUEUE_ITEM.toString());

		}catch(Exception exception) {
			log.debug("error data =>:"+exception.getMessage());
			return this.customerQueueServiceUtility.generateErrorRespose(requestModel,exception);		}
	}

	public ResponseModel recall(RequestModel requestModel) {
		log.debug("i am in the com.customer.queue.wrapper to recall queue item");
		log.debug(requestModel.toString());
		ServiceQueue serviceQueue=null;
		try{
			serviceQueue=customerQueueServiceUtility.validateForAll(requestModel);
			serviceQueue=customerQueueService.recall(serviceQueue);
			return this.customerQueueServiceUtility.generateSuccessResponse(requestModel,SuccessCodes.S_RECALLED_QUEUE_ITEM.toString());

		}catch(Exception exception) {
			log.debug("error data =>"+exception.getMessage());
			return this.customerQueueServiceUtility.generateErrorRespose(requestModel,exception);
		}

	}

	public ResponseModel getQueueForDay(RequestModel requestModel) throws Exception {
		log.debug("i am in the com.customer.queue.wrapper to get queue for the day");
		log.debug(requestModel.toString());
		ServiceQueue serviceQueue=null;

		serviceQueue=customerQueueServiceUtility.validateForgetQueueForDayAndGetSkippedToken(requestModel);
		List<ServiceQueue> serviceQueueData=customerQueueService.getQueueForDay(serviceQueue);
		if(!serviceQueueData.isEmpty())
			return this.customerQueueServiceUtility.generateSuccessResponse(requestModel,SuccessCodes.S_QUEUE_FOR_DAY_OBTAINED.toString());
		else
			return this.customerQueueServiceUtility.generateSuccessResponse(requestModel,SuccessCodes.S_NO_QUEUE_FOR_DAY.toString());

	}

	public ResponseModel markAsServiced(RequestModel requestModel) throws Exception {
		log.debug("i am in the com.customer.queue.wrapper to mark the item as serviced");
		log.debug(requestModel.toString());
		ServiceQueue serviceQueue=null;
		try{
			serviceQueue=customerQueueServiceUtility.validateForMarkAsServiced(requestModel);
			serviceQueue=customerQueueService.markAsServiced(serviceQueue);
			return this.customerQueueServiceUtility.generateSuccessResponse(requestModel,serviceQueue);
		}catch(Exception exception) {
			return this.customerQueueServiceUtility.generateErrorRespose(requestModel,exception);
		}
	}

	public ResponseModel getSkippedQueueItem(RequestModel requestModel) throws Exception {
		log.debug("i am in the com.customer.queue.wrapper to get skipped queue item");
		log.debug(requestModel.toString());
			ServiceQueue serviceQueue=customerQueueServiceUtility.validateForgetQueueForDayAndGetSkippedToken(requestModel);
			List<ServiceQueue> skippedQueueItem=customerQueueService.getSkippedQueueItem(serviceQueue);
			 if(!skippedQueueItem.isEmpty())
			return this.customerQueueServiceUtility.generateSuccessResponse(requestModel,SuccessCodes.S_SKIPPED_QUEUE_ITEM_OBTAINED.toString());
			else
			return this.customerQueueServiceUtility.generateSuccessResponse(requestModel,SuccessCodes.S_NO_SKIPPED_QUEUE_ITEMS.toString());
	
		
	}

	public ResponseModel recallItemInSkipQueue(RequestModel requestModel) throws Exception {
		log.debug("i am in the com.customer.queue.wrapper to recall skipped queue item");
		log.debug(requestModel.toString());
		ServiceQueue serviceQueue=null;
		try{
			serviceQueue=customerQueueServiceUtility.validateForAll(requestModel);
			serviceQueue=customerQueueService.recallItemInSkipQueue(serviceQueue);
			return this.customerQueueServiceUtility.generateSuccessResponse(requestModel,SuccessCodes.S_SKIPPED_QUEUE_ITEM_OBTAINED.toString());
		}catch(Exception exception) {
			return this.customerQueueServiceUtility.generateErrorRespose(requestModel,exception);
		}
		
	}

	public Integer getTotalNumberServicedToday(RequestModel requestModel) throws Exception {
		log.debug("i am in the com.customer.queue.wrapper to get TotalNumber Serviced Today");
		log.debug(requestModel.toString());
		ServiceQueue serviceQueue=customerQueueServiceUtility.validateForAll(requestModel);
		return customerQueueService.getTotalNumberServicedToday(serviceQueue);
	}
	public ResponseModel getTotallNumberServicedForCounter(RequestModel requestModel) throws Exception {
		log.debug("i am in the com.customer.queue.wrapper to get Totall Number Serviced For Counter");
        log.debug(requestModel.toString());
         
         ServiceQueue serviceQueue=customerQueueServiceUtility.validateForAll(requestModel);
		List<ServiceQueue> listOfServiceDataForCounter=customerQueueService.getTotallNumberServicedForCounter(serviceQueue);
		 if(!listOfServiceDataForCounter.isEmpty())
				return this.customerQueueServiceUtility.generateSuccessResponse(requestModel,SuccessCodes.S_SERVICE_DATA_FOR_COUNTER_OBTAINED.toString());
				else
				return this.customerQueueServiceUtility.generateSuccessResponse(requestModel,SuccessCodes.S_NO_SERVICE_DATA_FOR_COUNTER_OBTAINED.toString());
			
	}

}
