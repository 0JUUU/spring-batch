package io.springbatch.springbatch;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class ExecutionContextTasklet2 implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        System.out.println(">> step2 was executed");

        ExecutionContext jobExecutionContext = chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext();
        ExecutionContext stepExecutionContext = chunkContext.getStepContext().getStepExecution().getExecutionContext();

        // 앞에서 이미 tasklet1에서 저장하는 과정을 거침
        // step2를 하는 시점에선 jobExecution에 있는 내용은 step간 공유가 되기에 찾을 수 있고
        // stepExecution 내용은 step 간 공유가 안되어 없다.
        System.out.println("jobName : " + jobExecutionContext.get("jobName"));
        System.out.println("stepName : " + stepExecutionContext.get("stepName"));

        String stepName = chunkContext.getStepContext().getStepExecution().getStepName();
        if (stepExecutionContext.get("stepName") == null) {
            stepExecutionContext.put("stepName", stepName);
        }
        return RepeatStatus.FINISHED;
    }
}
