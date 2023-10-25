package com.fse.restaurant.springbatch.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fse.restaurant.springbatch.repository.RestaurantRepository;

@Component
public class JobCompletionNotificationListener implements JobExecutionListener {
	private static final Logger logger = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    @Autowired
    private RestaurantRepository restaurantRepository; 

    @Override
    public void beforeJob(JobExecution jobExecution) {
        
    	logger.info("Job Started...");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        // This method is called after the batch job completes.
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
        	logger.info("Job Completed Successfully!");

            long recordCount = restaurantRepository.count();
            logger.info("Records Inserted: " + recordCount);
        } else if (jobExecution.getStatus() == BatchStatus.FAILED) {
        	logger.error("Job Failed...");
        }
    }
}

