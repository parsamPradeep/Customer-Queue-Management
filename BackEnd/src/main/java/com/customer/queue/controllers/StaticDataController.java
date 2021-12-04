package com.customer.queue.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.customer.queue.model.RequestModel;
import com.customer.queue.model.ResponseModel;
import com.customer.queue.wrapper.StaticDataWrapper;
@RestController
public class StaticDataController {

   
   private Logger logger = LoggerFactory.getLogger(StaticDataController.class);
   private StaticDataWrapper staticDataWrapper;
   
   @Autowired
   StaticDataController(StaticDataWrapper staticDataWrapper){
	   this.staticDataWrapper=staticDataWrapper;
   }
  
   
   		@RequestMapping(value="/getAllServiceTypes", method= RequestMethod.POST)
 		public ResponseModel getAllServiceTypes(@RequestBody RequestModel requestModel) throws Exception {
   			logger.info("I am in the controller to get all service types to teller.");
   	        return this.staticDataWrapper.getAllServiceTypes(requestModel);
   	           			
	  }
   
   		@RequestMapping(value="/getServiceTypesForBranch", method= RequestMethod.POST)
 		public ResponseModel getServiceTypesForBranch(@RequestBody RequestModel requestModel) throws Exception {
   			logger.info("I am in the controller to get all service types to teller.");
   	        return this.staticDataWrapper.getServiceTypesForBranch(requestModel);
   	           			
	  }
   		@RequestMapping(value="/removeServiceType", method= RequestMethod.POST)
 		public ResponseModel removeServiceType(@RequestBody RequestModel requestModel) throws Exception {
   			logger.info("I am in the controller to remove service type.");
   	        return this.staticDataWrapper.removeServiceType(requestModel);
   	           			
	  }
   		@RequestMapping(value="/addServiceType", method= RequestMethod.POST)
 		public ResponseModel addServiceType(@RequestBody RequestModel requestModel) throws Exception {
   			logger.info("I am in the controller to add service type.");
   	        return this.staticDataWrapper.addServiceType(requestModel);
   	           			
	  }
   		
   		@RequestMapping(value="/getAllCounters", method= RequestMethod.POST)
 		public ResponseModel getAllCounters(@RequestBody RequestModel requestModel) throws Exception {
   			logger.info("I am in the controller to get all counters.");
   	        return this.staticDataWrapper.getAllCounters(requestModel);
   	           			
	  }
   		@RequestMapping(value="/removeCounter", method= RequestMethod.POST)
 		public ResponseModel removeCounter(@RequestBody RequestModel requestModel) throws Exception {
   			logger.info("I am in the controller to remove counter.");
   	        return this.staticDataWrapper.removeCounter(requestModel);
   	           			
	  }
   		@RequestMapping(value="/addCounter", method= RequestMethod.POST)
 		public ResponseModel addCounter(@RequestBody RequestModel requestModel) throws Exception {
   			logger.info("I am in the controller to addCounter.");
   	        return this.staticDataWrapper.addCounter(requestModel);
   	           			
	  }
   		
   		
   		@RequestMapping(value="/getAllServiceTypeForCounter", method= RequestMethod.POST)
 		public ResponseModel getAllServiceTypeForCounter(@RequestBody RequestModel requestModel) throws Exception {
   			logger.info("I am in the controller to get all serviceTypeForCounter.");
   	        return this.staticDataWrapper.getAllServiceTypeForCounter(requestModel);
   	           			
	  }
   		@RequestMapping(value="/removeServiceTypeForCounter", method= RequestMethod.POST)
 		public ResponseModel removeServiceTypeForCounter(@RequestBody RequestModel requestModel) throws Exception {
   			logger.info("I am in the controller to remove serviceTypeForCounter.");
   	        return this.staticDataWrapper.removeServiceTypeForCounter(requestModel);
   	           			
	  }
   		@RequestMapping(value="/addServiceTypeForCounter", method= RequestMethod.POST)
 		public ResponseModel addServiceTypeForCounter(@RequestBody RequestModel requestModel) throws Exception {
   			logger.info("I am in the controller to add ServiceTypeForCounter.");
   	        return this.staticDataWrapper.addServiceTypeForCounter(requestModel);
   	           			
	  }
   		
   		@RequestMapping(value="/getServiceTypeForCounter", method= RequestMethod.POST)
 		public ResponseModel getServiceTypeForCounter(@RequestBody RequestModel requestModel) throws Exception {
   			logger.info("I am in the controller to add ServiceTypeForCounter.");
   	        return this.staticDataWrapper.getServiceTypeForCounter(requestModel);
   	           			
	  }
   		@RequestMapping(value="/getCampainImageURL",method=RequestMethod.POST)
   			public String[] getCampainURLForBranch(@RequestBody RequestModel requestModel) throws Exception{
   			logger.info("I am inside controller to get CampainURL's");
   			return this.staticDataWrapper.getCampainURLForBranch(requestModel);
   			
   		}
   				
}
