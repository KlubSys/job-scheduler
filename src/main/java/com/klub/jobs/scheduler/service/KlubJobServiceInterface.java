package com.klub.jobs.scheduler.service;

import com.klub.jobs.scheduler.exception.ErrorOccurredException;
import com.klub.jobs.scheduler.model.dto.CreateJobInput;
import com.klub.jobs.scheduler.model.entity.KlubJob;

import java.util.Map;

public interface KlubJobServiceInterface {

    KlubJob createJob(CreateJobInput input) throws ErrorOccurredException;
    KlubJob updateJob(KlubJob job, Map<String, Object> data) throws ErrorOccurredException;
}
