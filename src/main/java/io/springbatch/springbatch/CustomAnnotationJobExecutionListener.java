package io.springbatch.springbatch;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.BeforeJob;

public class CustomAnnotationJobExecutionListener {

    @BeforeJob
    public void bJob(JobExecution jobExecution) {
        System.out.println(">> Job is started");
        System.out.println(">> jobName : " + jobExecution.getJobInstance().getJobName());
    }

    @AfterJob
    public void aJob(JobExecution jobExecution) {
        long startTime = jobExecution.getStartTime().getTime();
        long endTime = jobExecution.getEndTime().getTime();
        System.out.println(">> Total Time : " + (endTime - startTime));
    }
}
