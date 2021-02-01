package com.customer.queu.wrapper;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.customer.queue.codes.ErrorCodes;
import com.customer.queue.codes.SuccessCodes;
import com.customer.queue.entities.Counter;
import com.customer.queue.entities.ServiceType;
import com.customer.queue.entities.ServiceTypeForCounter;
import com.customer.queue.model.RequestModel;
import com.customer.queue.model.ResponseModel;
import com.customer.queue.services.CustomerQueueServiceUtility;
import com.customer.queue.services.StaticDataService;
@Component
public class StaticDataWrapper {
	 Logger log= LoggerFactory.getLogger(CustomerQueueWrapper.class);
	    private CustomerQueueServiceUtility customerQueueServiceUtility;
	    private StaticDataService StaticDataService;
	   
	    @Autowired
	    StaticDataWrapper(CustomerQueueServiceUtility customerQueueServiceUtility,StaticDataService StaticDataService) {
	    	super();
	       this.StaticDataService=StaticDataService;
	       this.customerQueueServiceUtility=customerQueueServiceUtility;       
	    }

		public ResponseModel getAllServiceTypes(RequestModel requestModel) throws Exception {
			 log.debug("i am in the com.customer.queue.wrapper to get all service types");
		     log.debug(requestModel.toString()); 
		        		List<ServiceType> allServiceTypeData=StaticDataService.getAllServiceTypes();
		        		if(!allServiceTypeData.isEmpty()) 
		            	return this.customerQueueServiceUtility.generateSuccessResponse(requestModel,SuccessCodes.S_SERVICE_TYPES_OBTAINED.toString());
		        		else 
		        		return this.customerQueueServiceUtility.generateSuccessResponse(requestModel,SuccessCodes.S_NO_SERVICE_TYPE_DATA.toString());
		        		     
		}

		public ResponseModel removeServiceType(RequestModel requestModel) {
			 log.debug("i am in the com.customer.queue.wrapper to remove service type");
		        log.debug(requestModel.toString());
		        ServiceType serviceType=null;
		        try{	
		        	serviceType=customerQueueServiceUtility.validateForRemoveServiceType(requestModel);
		        	List<ServiceType> deletedServiceTypeData=StaticDataService.removeServiceType(serviceType);
		            	return this.customerQueueServiceUtility.generateSuccessResponse(requestModel,SuccessCodes.S_SERVICE_TYPE_REMOVED.toString());
		  
		        }catch(Exception exception) {
		        		return this.customerQueueServiceUtility.generateErrorRespose(requestModel,exception);
		        }
		}

		public ResponseModel addServiceType(RequestModel requestModel) {
			 log.debug("i am in the com.customer.queue.wrapper to add service type");
		        log.debug(requestModel.toString());
		        ServiceType serviceType=null;
		        try{	
		        	serviceType=customerQueueServiceUtility.validateForAddServiceType(requestModel);
		        	ServiceType serviceTypeDataUponAddition=StaticDataService.addServiceType(serviceType);
		            	return this.customerQueueServiceUtility.generateSuccessResponse(requestModel,SuccessCodes.S_SERVICE_TYPE_ADDED.toString());
		  
		        }catch(Exception exception) {
		        		return this.customerQueueServiceUtility.generateErrorRespose(requestModel,exception);
		        }
		}

		public ResponseModel getAllCounters(RequestModel requestModel) throws Exception {
			 log.debug("i am in the com.customer.queue.wrapper to get all counters");
		        log.debug(requestModel.toString());
		        	 Counter counter=customerQueueServiceUtility.validateForGetAllCounters(requestModel);
		        		List<Counter> counterData=StaticDataService.getAllCounters(counter);
		        		if(!counterData.isEmpty())
		            	return this.customerQueueServiceUtility.generateSuccessResponse(requestModel,SuccessCodes.S_COUNTERS_OBTAINED.toString());
		        		else
		        		return this.customerQueueServiceUtility.generateSuccessResponse(requestModel,SuccessCodes.S_NO_COUNTERS_DATA.toString());
		        
		}

		public ResponseModel removeCounter(RequestModel requestModel) {
			log.debug("i am in the com.customer.queue.wrapper to remove counter");
	        log.debug(requestModel.toString());
	        Counter counter=null;
	        try{	
	        	counter=customerQueueServiceUtility.validateForRemoveCounter(requestModel);
	        		Counter counterData=StaticDataService.removeCounter(counter);
	            	return this.customerQueueServiceUtility.generateSuccessResponse(requestModel,SuccessCodes.S_COUNTERS_REMOVED.toString());
	  
	        }catch(Exception exception) {
	        		return this.customerQueueServiceUtility.generateErrorRespose(requestModel,exception);
	        }
		}

		public ResponseModel addCounter(RequestModel requestModel) {
			log.debug("i am in the com.customer.queue.wrapper to add counter");
	        log.debug(requestModel.toString());
	        Counter counter=null;
	        try{	
	        	counter=customerQueueServiceUtility.validateForAddingCounter(requestModel);
	        		Counter counterData=StaticDataService.addCounter(counter);
	            	return this.customerQueueServiceUtility.generateSuccessResponse(requestModel,SuccessCodes.S_COUNTER_ADDED.toString());
	  
	        }catch(Exception exception) {
	        		return this.customerQueueServiceUtility.generateErrorRespose(requestModel,exception);
	        }
		}
		public ResponseModel addServiceTypeForCounter(RequestModel requestModel) {
			log.debug("i am in the com.customer.queue.wrapper to add ServiceTypeForCounter");
	        log.debug(requestModel.toString());
	        ServiceTypeForCounter serviceTypeForCounter=null;
	        try{	
	        	serviceTypeForCounter=customerQueueServiceUtility.validateForAddServiceTypeForCounter(requestModel);
	        	serviceTypeForCounter=StaticDataService.addServiceTypeForCounter(serviceTypeForCounter);
	            	return this.customerQueueServiceUtility.generateSuccessResponse(requestModel,SuccessCodes.S_SERVICE_TYPE_FOR_COUNTER_ADDED.toString());
	  
	        }catch(Exception exception) {
	        		return this.customerQueueServiceUtility.generateErrorRespose(requestModel,exception);
	        }
		}
		
		public ResponseModel removeServiceTypeForCounter(RequestModel requestModel) {
			log.debug("i am in the com.customer.queue.wrapper to add ServiceTypeForCounter");
	        log.debug(requestModel.toString());
	        ServiceTypeForCounter serviceTypeForCounter=null;
	        try{	
	        	serviceTypeForCounter=customerQueueServiceUtility.validateForAddServiceTypeForCounter(requestModel);
	        	serviceTypeForCounter=StaticDataService.removeServiceTypeForCounter(serviceTypeForCounter);
	            	return this.customerQueueServiceUtility.generateSuccessResponse(requestModel,SuccessCodes.S_SERVICE_TYPE_FOR_COUNTER_REMOVED.toString());
	  
	        }catch(Exception exception) {
	        		return this.customerQueueServiceUtility.generateErrorRespose(requestModel,exception);
	        }
		}
		
		public ResponseModel getAllServiceTypeForCounter(RequestModel requestModel) throws Exception {
			log.debug("i am in the com.customer.queue.wrapper to get all ServiceTypeForCounter");
	        log.debug(requestModel.toString());
	        List<ServiceTypeForCounter> serviceTypeForCounterData=StaticDataService.getAllServiceTypeForCounter();
    		if(!serviceTypeForCounterData.isEmpty())
        	return this.customerQueueServiceUtility.generateSuccessResponse(requestModel,SuccessCodes.S_SERVICE_TYPE_FOR_COUNTER_OBTAINED.toString());
    		else
    		return this.customerQueueServiceUtility.generateSuccessResponse(requestModel,SuccessCodes.S_NO_SERVICE_TYPE_FOR_COUNTER_DATA.toString());
		}

		public ResponseModel getServiceTypeForCounter(RequestModel requestModel) throws Exception {
			log.debug("i am in the com.customer.queue.wrapper to get ServiceTypeForCounter");
	        log.debug(requestModel.toString());
	         	ServiceTypeForCounter serviceTypeForCounter=customerQueueServiceUtility.validateForgetServiceTypeForCounter(requestModel);
	        	List<ServiceTypeForCounter> serviceTypeForCounterData=StaticDataService.getServiceTypeForCounter(serviceTypeForCounter);
	        	if(!serviceTypeForCounterData.isEmpty())	
	        	return this.customerQueueServiceUtility.generateSuccessResponse(requestModel,SuccessCodes.S_SERVICE_TYPE_FOR_COUNTER_OBTAINED.toString());
	        	else
	        	return this.customerQueueServiceUtility.generateSuccessResponse(requestModel,SuccessCodes.S_NO_SERVICE_TYPE_FOR_COUNTER_DATA.toString());
	       
		}

		public String[] getCampainURLForBranch(RequestModel requestModel) throws Exception {
			log.debug("i am in the com.customer.queue.wrapper to get CampainURL's");
	        log.debug(requestModel.toString());
	        if(requestModel.getBranchCode()==null) {
	        	log.error("Counter Id can't  be blank");
				throw new Exception(ErrorCodes.E_BRANCH_CODECBS_MANDATORY.toString());
	        }
			return this.customerQueueServiceUtility.getCampainURLForBranch(requestModel);
		}

	


}
