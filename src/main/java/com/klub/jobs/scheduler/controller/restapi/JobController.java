package com.klub.jobs.scheduler.controller.restapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.klub.jobs.scheduler.controller.dto.SubmitJobRequest;
import com.klub.jobs.scheduler.exception.ErrorOccurredException;
import com.klub.jobs.scheduler.exception.NotFoundException;
import com.klub.jobs.scheduler.helper.JobExecutionStatusEnum;
import com.klub.jobs.scheduler.helper.JobStatusEnum;
import com.klub.jobs.scheduler.helper.JobTypeEnum;
import com.klub.jobs.scheduler.model.dto.CreateJobInput;
import com.klub.jobs.scheduler.model.entity.KlubJob;
import com.klub.jobs.scheduler.repository.KlubJobRepositoryInterface;
import com.klub.jobs.scheduler.service.KlubJobServiceInterface;
import com.klub.jobs.scheduler.service.api.CentralLoggerServerApi;
import com.klub.jobs.scheduler.service.api.dto.CentralServerLogMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("/api/jobs")
@CrossOrigin("*")
public class JobController {

    private final KlubJobServiceInterface klubJobService;
    private final KlubJobRepositoryInterface klubJobRepository;
    private final ObjectMapper defaultMapper;
    private final CentralLoggerServerApi centralLoggerServerApi;

    @Autowired
    public JobController(KlubJobServiceInterface klubJobService,
                         KlubJobRepositoryInterface klubJobRepository,
                         ObjectMapper defaultMapper, CentralLoggerServerApi centralLoggerServerApi) {
        this.klubJobService = klubJobService;
        this.klubJobRepository = klubJobRepository;
        this.defaultMapper = defaultMapper;
        this.centralLoggerServerApi = centralLoggerServerApi;
    }

    @PostMapping
    ResponseEntity<Map<String, Object>> submitJob(@RequestBody SubmitJobRequest body)
            throws Exception {
        CreateJobInput input = new CreateJobInput();
        input.setJobInterval(body.getJobInterval());
        input.setJobType(input.getJobType());
        input.setAction(body.getAction());
        input.setResultLocation(body.getResultLocation());
        input.setPayload(body.getPayload());

        centralLoggerServerApi.dispatchLog(CentralServerLogMessage.builder()
                .text("Creating Job").build());

        KlubJob job = klubJobService.createJob(input);
        Map<String, Object> response = new HashMap<>();
        response.put("job_id", job.getId());
        centralLoggerServerApi.dispatchLog(CentralServerLogMessage.builder()
                .text("Job Created " + job.getId() + " and scheduled").build());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("{id}")
    void updateJob(@PathVariable("id") String jobId, @RequestBody String body)
            throws JsonProcessingException, ErrorOccurredException, NotFoundException {
        HashMap<String, Object> data = defaultMapper.readValue(body, HashMap.class);

        Optional<KlubJob> jobCtn = klubJobRepository.findById(jobId);
        if (jobCtn.isEmpty()) throw new NotFoundException("Job not found");

        if (data.get("job_status") != null){
            data.replace("job_status", JobStatusEnum.valueOf((String) data.get("job_status")));
        }

        if (data.get("job_type") != null){
            data.replace("job_type", JobTypeEnum.valueOf((String) data.get("job_type")));
        }

        if (data.get("execution_status") != null){
            data.replace("execution_status",
                    JobExecutionStatusEnum.valueOf((String) data.get("execution_status")));
        }

        klubJobService.updateJob(jobCtn.get(), data);
    }
}
