package io.springbatch.springbatch;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

public class CustomDecider implements JobExecutionDecider {

    private int count = 1;

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        count++;
        System.out.println(">> count " + count);
        if(count % 2 == 0) {
            return new FlowExecutionStatus("EVEN");
        }
        return new FlowExecutionStatus("ODD");
    }
}
