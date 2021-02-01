package com.customer.queue.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.customer.queue.codes.ErrorCodes;
import com.customer.queue.entities.Counter;
import com.customer.queue.entities.CounterOccupancy;
import com.customer.queue.repos.CounterOccupancyRepo;
import com.customer.queue.repos.CounterRepo;

@Transactional(rollbackFor = Exception.class)
@Service
public class CounterOccupancyService {
	private Logger logger = LoggerFactory.getLogger(CustomerQueueService.class);
	private CounterRepo counterRepo;
	private CounterOccupancyRepo counterOccupancyRepo;

	@Autowired
	CounterOccupancyService( CounterRepo counterRepo,
			 CounterOccupancyRepo counterOccupancyRepo) {
		this.counterOccupancyRepo = counterOccupancyRepo;
		this.counterRepo = counterRepo;
	}

	public List<Counter> getAvailableCounter(CounterOccupancy counterOccupancy) throws Exception {
		logger.debug("Im in service to get available counters");
		try {
			List<Counter> countersInBranch = counterRepo.findByBranchCode(counterOccupancy.getBranchCode());
			List<CounterOccupancy> counterOccupied = counterOccupancyRepo
					.findByBranchCode(counterOccupancy.getBranchCode());
			List<Long> counterToRemove = new ArrayList<Long>();
			if (!counterOccupied.isEmpty()) {
				for (int i = 0; i < countersInBranch.size(); i++) {
					for (int j = 0; j < counterOccupied.size(); j++) {
						if (countersInBranch.get(i).getCounterId() == counterOccupied.get(j).getCounterNumber()) {
							counterToRemove.add(countersInBranch.get(i).getCounterId());
						}
					}

				}
				countersInBranch = counterRepo.findByCounterIdNotIn(counterToRemove);
				return countersInBranch;
			} else {
				return countersInBranch;
			}
		} catch (Exception e) {
			logger.error("Got error while persisting!", e);
			throw new Exception(e.getMessage());
		}
	}

	public CounterOccupancy occupyCounter(CounterOccupancy counterOccupancy) throws Exception {
		logger.debug("Im in service to occupy counters");
		try {
			Optional<CounterOccupancy> userCounterMappingData = counterOccupancyRepo
					.findByBranchCodeAndAllocatedId(counterOccupancy.getBranchCode(), counterOccupancy.getAllocatedId());
			if (!userCounterMappingData.isPresent()) {
				Optional<CounterOccupancy> counter = counterOccupancyRepo
						.findByCounterNumber(counterOccupancy.getCounterNumber());
				if (!counter.isPresent()) {
					counterOccupancyRepo.save(counterOccupancy);
				} else {
					throw new Exception(ErrorCodes.E_COUNTER_ALREADY_OCCUPIED.toString());
				}
			} else {
				throw new Exception(ErrorCodes.E_SAME_USER_CANT_OCCUPY_TWO_COUNTERS.toString());
			}
		} catch (Exception exception) {
			logger.error("Got error while persisting!", exception);
			throw exception;
		}
		return counterOccupancy;

	}

	public CounterOccupancy releaseCounter(CounterOccupancy counterOccupancy) throws Exception {
		logger.debug("Im in service to occupy counters");
		try {
			Optional<CounterOccupancy> counter = counterOccupancyRepo
					.findByCounterNumber(counterOccupancy.getCounterNumber());
			if (counter.isPresent()) {
				counterOccupancyRepo.deleteById(counterOccupancy.getCounterNumber());
			} else {
				throw new Exception(ErrorCodes.E_COUNTER_ALREADY_RELEASED.toString());
			}
		} catch (Exception exception) {
			logger.error("Got error while persisting!", exception);
			throw exception;

		} 
		return counterOccupancy;
	}

}
