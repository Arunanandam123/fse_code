package com.fse.restaurant.springbatch.config;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fse.restaurant.springbatch.repository.RestaurantRepository;

@Component
public class JobCompletionNotificationListener implements JobExecutionListener {

    @Autowired
    private RestaurantRepository restaurantRepository; 

    @Override
    public void beforeJob(JobExecution jobExecution) {
        
        System.out.println("Job Started...");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        // This method is called after the batch job completes.
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            System.out.println("Job Completed Successfully!");

            long recordCount = restaurantRepository.count();
            System.out.println("Records Inserted: " + recordCount);
        } else if (jobExecution.getStatus() == BatchStatus.FAILED) {
            System.err.println("Job Failed...");
        }
    }
}

