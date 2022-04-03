package io.springbatch.springbatch;

import java.util.Iterator;
import java.util.Set;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.job.SimpleJob;
import org.springframework.batch.core.launch.JobExecutionNotRunningException;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobController {

    @Autowired
    private JobRegistry jobRegistry;

    @Autowired
    private JobExplorer jobExplorer;

    @Autowired
    private JobOperator jobOperator;

    @PostMapping(value = "/batch/start")
    public String start(@RequestBody JobInfo jobInfo) throws NoSuchJobException, JobInstanceAlreadyExistsException, JobParametersInvalidException {
        for (Iterator<String> iterator = jobRegistry.getJobNames().iterator(); iterator.hasNext(); ) {
            SimpleJob job = (SimpleJob) jobRegistry.getJob(iterator.next());
            System.out.println("jobName = " + job.getName());
            jobOperator.start(job.getName(), "id=" + jobInfo.getId());
        }
        return "batch is started";
    }

    @PostMapping(value = "/batch/stop")
    public String stop() throws NoSuchJobException, NoSuchJobExecutionException, JobExecutionNotRunningException {
        for (Iterator<String> iterator = jobRegistry.getJobNames().iterator(); iterator.hasNext(); ) {
            SimpleJob job = (SimpleJob) jobRegistry.getJob(iterator.next());
            System.out.println("jobName = " + job.getName());

            Set<JobExecution> runningJobExecutions = jobExplorer.findRunningJobExecutions(job.getName());
            JobExecution jobExecution = runningJobExecutions.iterator().next();

            jobOperator.stop(jobExecution.getId());
        }

        return "batch is stopped";
    }

    @PostMapping(value = "/batch/restart")
    public String restart()
        throws NoSuchJobException, NoSuchJobExecutionException, JobInstanceAlreadyCompleteException, JobParametersInvalidException, JobRestartException {
        for (Iterator<String> iterator = jobRegistry.getJobNames().iterator(); iterator.hasNext(); ) {
            SimpleJob job = (SimpleJob) jobRegistry.getJob(iterator.next());
            System.out.println("jobName = " + job.getName());

            JobInstance lastJobInstance = jobExplorer.getLastJobInstance(job.getName());
            JobExecution lastJobExecution = jobExplorer.getLastJobExecution(lastJobInstance);
            jobOperator.restart(lastJobExecution.getId());
        }

        return "batch is restarted";
    }
}
