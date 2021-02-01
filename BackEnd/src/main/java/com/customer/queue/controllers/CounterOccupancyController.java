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
import com.customer.queue.wrapper.CounterOccupancyWrapper;

@RestController
public class CounterOccupancyController {
	private Logger logger = LoggerFactory.getLogger(StaticDataController.class);
	private CounterOccupancyWrapper counterOccupancyWrapper;
	
	 @Autowired
	 CounterOccupancyController(CounterOccupancyWrapper counterOccupancyWrapper){
		 this.counterOccupancyWrapper=counterOccupancyWrapper;
	 }
	 
	 @RequestMapping(value="/getAvailableCounter", method= RequestMethod.POST)
		public ResponseModel getAllServiceTypes(@RequestBody RequestModel requestModel) throws Exception {
			logger.info("I am in the controller to get available counter teller.");
	        return this.counterOccupancyWrapper.getAvailableCounter(requestModel);
	           			
	  }
	 @RequestMapping(value="/occupyCounter", method= RequestMethod.POST)
		public ResponseModel occupyCounter(@RequestBody RequestModel requestModel) throws Exception {
			logger.info("I am in the controller to occupy counter.");
	        return this.counterOccupancyWrapper.occupyCounter(requestModel);
	           			
	  }
	 @RequestMapping(value="/releaseCounter", method= RequestMethod.POST)
		public ResponseModel releaseCounter(@RequestBody RequestModel requestModel) throws Exception {
			logger.info("I am in the controller to occupy counter.");
	        return this.counterOccupancyWrapper.releaseCounter(requestModel);
	           			
	  }

}
