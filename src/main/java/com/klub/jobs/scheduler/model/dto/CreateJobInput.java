package com.klub.jobs.scheduler.model.dto;

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
public class CreateJobInput {

    private JobTypeEnum jobType;
    private JobActionEnum action;
    private Integer jobInterval;
    private String resultLocation;
    private String payload;
}
