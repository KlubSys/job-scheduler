package com.klub.jobs.scheduler.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.klub.jobs.scheduler.helper.JobActionEnum;
import com.klub.jobs.scheduler.helper.JobTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmitJobRequest {

    @JsonProperty("job_type")
    private JobTypeEnum jobType;

    @JsonProperty("action")
    private JobActionEnum action;

    @JsonProperty("job_interval")
    private Integer jobInterval;

    @JsonProperty("result_location")
    private String resultLocation;

    private String payload;
}
