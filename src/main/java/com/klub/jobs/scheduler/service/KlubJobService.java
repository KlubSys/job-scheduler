package com.klub.jobs.scheduler.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.klub.jobs.scheduler.exception.ErrorOccurredException;
import com.klub.jobs.scheduler.helper.JobActionEnum;
import com.klub.jobs.scheduler.helper.JobExecutionStatusEnum;
import com.klub.jobs.scheduler.helper.JobStatusEnum;
import com.klub.jobs.scheduler.helper.JobTypeEnum;
import com.klub.jobs.scheduler.helper.utils.CamelCaseUtils;
import com.klub.jobs.scheduler.helper.utils.DateOperationUtils;
import com.klub.jobs.scheduler.helper.utils.MethodInvocationUtils;
import com.klub.jobs.scheduler.model.dto.CreateJobInput;
import com.klub.jobs.scheduler.model.entity.KlubJob;
import com.klub.jobs.scheduler.model.entity.KlubScheduledJob;
import com.klub.jobs.scheduler.repository.KlubJobRepositoryInterface;
import com.klub.jobs.scheduler.repository.KlubScheduledJobRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class KlubJobService implements KlubJobServiceInterface {

    private final KlubScheduledJobRepositoryInterface klubScheduledJobRepository;
    private final KlubJobRepositoryInterface klubJobRepository;
    private final ObjectMapper defaultMapper;

    @Autowired
    public KlubJobService(KlubScheduledJobRepositoryInterface klubScheduledJobRepository,
                          KlubJobRepositoryInterface klubJobRepository, ObjectMapper defaultMapper) {
        this.klubScheduledJobRepository = klubScheduledJobRepository;
        this.klubJobRepository = klubJobRepository;
        this.defaultMapper = defaultMapper;
    }


    @Override
    public KlubJob createJob(CreateJobInput input) throws ErrorOccurredException {
        try {
            KlubJob job = new KlubJob();
            KlubScheduledJob scheduledJob = new KlubScheduledJob();

            if (input.getJobInterval() != null) {
                job.setJobInterval(input.getJobInterval());
            }
            job.setMaxRetires(5); //TODO sue constant
            job.setJobStatus(JobStatusEnum.NOT_STARTED);

            if (input.getAction() == JobActionEnum.FILE_DECOMPOSITION) {
                job.setJobType(JobTypeEnum.RECURRENT);
            }

            job.setPayload(input.getPayload());
            //Times
            job.setScheduledJobExecutionTime(DateOperationUtils.addSeconds(
                    new Date(),
                    1 //TODO use a constant variable
            ));
            job = klubJobRepository.save(job);

            //We will update the upload with the job metadata information
            HashMap<String, Object> pl = (HashMap<String, Object>) defaultMapper.readValue(
                    input.getPayload(), HashMap.class);
            pl.put("job_id", job.getId());
            pl.put("job_status", JobStatusEnum.NOT_STARTED.name());
            job.setPayload(defaultMapper.writeValueAsString(pl));
            job = klubJobRepository.save(job);

            scheduledJob.setJob(job);
            scheduledJob.setShardId(1); //TODO using a random and load balanced approach
            scheduledJob.setScheduledJobExecutionTime(job.getScheduledJobExecutionTime());
            scheduledJob = klubScheduledJobRepository.save(scheduledJob);

            return job;
        } catch (Exception e){
            e.printStackTrace();
            throw new ErrorOccurredException(e.getMessage());
        }
    }

    @Override
    public KlubJob updateJob(KlubJob job, Map<String, Object> data) throws ErrorOccurredException {

        try {
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                //TODO use a container for attribute to escape
                MethodInvocationUtils.set(
                        KlubJob.class,
                        job,
                        CamelCaseUtils.fromSnakeCase(entry.getKey()),
                        entry.getValue());
            }

            if(job.getJobStatus() == JobStatusEnum.STARTED){
                job.setActualJobExecutionTime(new Date());
            }
            return klubJobRepository.save(job);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorOccurredException(e.getMessage());
        }
    }
}
