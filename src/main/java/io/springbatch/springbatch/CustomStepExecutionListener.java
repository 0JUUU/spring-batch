package io.springbatch.springbatch;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class CustomStepExecutionListener implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {
        stepExecution.getExecutionContext().putString("name2", "user2");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }
}
