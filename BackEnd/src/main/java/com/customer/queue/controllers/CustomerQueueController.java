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
import com.customer.queue.wrapper.CustomerQueueWrapper;

@RestController
public class CustomerQueueController {

    private Logger logger = LoggerFactory.getLogger(CustomerQueueController.class);
    private CustomerQueueWrapper customerQueueWrapper;
    @Autowired
     CustomerQueueController(CustomerQueueWrapper customerQueueWrapper) {
        this.customerQueueWrapper = customerQueueWrapper;
       
    }    
    @RequestMapping(value="/requestNewToken", method= RequestMethod.POST,  produces = "application/json")  
    public ResponseModel requestNewToken(@RequestBody RequestModel requestModel) throws Exception {
        logger.info("I am in the controller to generate token number.");
        return this.customerQueueWrapper.requestNewToken(requestModel);
    }
    @RequestMapping(value="/allocateNextQueueItem", method = RequestMethod.POST)
    public ResponseModel allocateNextQueueItem(@RequestBody RequestModel requestModel) throws Exception {
        logger.info("I am in the controller to allocate next queueItem to teller.");
        return this.customerQueueWrapper.allocateNextQueueItem(requestModel);
    }
    @RequestMapping(value="/skipQueueItem", method = RequestMethod.POST)
    public ResponseModel skipQueueItem(@RequestBody RequestModel requestModel) throws Exception {
        logger.info("I am in the controller to allocate next queueItem to teller.");
        return this.customerQueueWrapper.skipQueueItem(requestModel);
    }
    @RequestMapping(value="/recall", method = RequestMethod.POST)
    public ResponseModel recall(@RequestBody RequestModel requestModel) throws Exception {
        logger.info("I am in the controller to allocate next queueItem to teller.");
        return this.customerQueueWrapper.recall(requestModel);
    }
    @RequestMapping(value="/getQueueForDay", method = RequestMethod.POST)
    public ResponseModel getQueueForDay(@RequestBody RequestModel requestModel) throws Exception {
        logger.info("I am in the controller to allocate next queueItem to teller.");
        return this.customerQueueWrapper.getQueueForDay(requestModel);
    }
    @RequestMapping(value="/markAsServiced", method= RequestMethod.POST)  
    public ResponseModel markAsServiced(@RequestBody RequestModel requestModel) throws Exception {
        logger.info("I am in the controller to get next token number.");
        return this.customerQueueWrapper.markAsServiced(requestModel);
    }   
    @RequestMapping(value="/getSkippedQueueItem", method= RequestMethod.POST)  
    public ResponseModel getSkippedQueueItem(@RequestBody RequestModel requestModel) throws Exception {
        logger.info("I am in the controller to get next token number.");
        return this.customerQueueWrapper.getSkippedQueueItem(requestModel);
    }   

    @RequestMapping(value="/recallItemInSkipQueue", method= RequestMethod.POST)  
    public ResponseModel recallItemInSkipQueue(@RequestBody RequestModel requestModel) throws Exception {
        logger.info("I am in the controller to get next token number.");
        return this.customerQueueWrapper.recallItemInSkipQueue(requestModel);
    }   

    @RequestMapping(value="/getTotalNumberServicedToday", method= RequestMethod.POST)  
    public Integer getTotalNumberServicedToday(@RequestBody RequestModel requestModel) throws Exception {
        logger.info("I am in the controller to get next token number.");
        return this.customerQueueWrapper.getTotalNumberServicedToday(requestModel);
    } 
    @RequestMapping(value="/getTotallNumberServicedForCounter",method=RequestMethod.POST)
	public ResponseModel getTotallNumberServicedForCounter(@RequestBody RequestModel requestModel) throws Exception{
	logger.info("I am inside controller to get CampainURL's");
	return this.customerQueueWrapper.getTotallNumberServicedForCounter(requestModel);
	
}   


}
