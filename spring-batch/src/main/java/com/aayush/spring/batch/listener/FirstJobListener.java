package com.aayush.spring.batch.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class FirstJobListener implements JobExecutionListener {

	@Override
	public void beforeJob(JobExecution jobExecution) {
		System.out.println("Before Job : " + jobExecution.getJobInstance().getJobName());
		System.out.println("Before Params : " + jobExecution.getJobParameters());
		System.out.println("Before  Execution : " + jobExecution.getExecutionContext());
		
		jobExecution.getExecutionContext().put("batch", "batch-value");
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		System.out.println("After Job : " + jobExecution.getJobInstance().getJobName());
		System.out.println("After Params : " + jobExecution.getJobParameters());
		System.out.println("After  Execution : " + jobExecution.getExecutionContext());
	}

}
