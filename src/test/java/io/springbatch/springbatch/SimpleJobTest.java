package io.springbatch.springbatch;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterChunkError;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBatchTest
@SpringBootTest(classes = {SimpleJobConfiguration.class, TestBatchConfig.class})
public class SimpleJobTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void clear() {
        jdbcTemplate.execute("delete from customer2");
    }

    @Test
    public void simpleJob_test() throws Exception {
        // given
        JobParameters jobParameters = new JobParametersBuilder()
            .addString("name", "user1")
            .addLong("date", new Date().getTime())
            .toJobParameters();

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        // then
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);
    }

    @Test
    public void step_test() throws Exception {
        // given
        JobParameters jobParameters = new JobParametersBuilder()
            .addString("name", "user1")
            .addLong("date", new Date().getTime())
            .toJobParameters();

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchStep("step1");
        StepExecution stepExecution = (StepExecution) ((List) jobExecution.getStepExecutions()).get(0);

        // then
        assertThat(stepExecution.getCommitCount()).isEqualTo(11);       // null을 리턴하기 때문에 -> 이때도 커밋이 한 번 일어남
        assertThat(stepExecution.getReadCount()).isEqualTo(1000);
        assertThat(stepExecution.getWriteCount()).isEqualTo(1000);
    }
}
