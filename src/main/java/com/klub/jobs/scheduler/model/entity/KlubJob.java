package com.klub.jobs.scheduler.model.entity;

import com.klub.jobs.scheduler.helper.JobExecutionStatusEnum;
import com.klub.jobs.scheduler.helper.JobStatusEnum;
import com.klub.jobs.scheduler.helper.JobTypeEnum;
import com.klub.jobs.scheduler.model.UuidAsIdEntity;

import javax.persistence.*;
import java.util.Date;

@Table(name = "klub_job")
@Entity
public class KlubJob extends UuidAsIdEntity {

    @Column(name = "actual_job_execution_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actualJobExecutionTime;

    @Column(name = "job_status")
    private JobStatusEnum jobStatus;

    @Column(name = "job_type")
    private JobTypeEnum jobType;

    /**
     * If recurrent
     */
    @Column(name = "job_interval")
    private Integer jobInterval;

    @Column(name = "result_location")
    private String resultLocation;

    @Column(name = "current_retries")
    private Integer currentRetires;

    @Column(name = "max_retries")
    private Integer maxRetires;

    @Column(name = "scheduled_job_execution_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date  scheduledJobExecutionTime;

    @Column(name = "execution_status")
    private JobExecutionStatusEnum executionStatus;

    @Column(name = "payload", columnDefinition = "text")
    private String payload;

    public KlubJob() {
        super();
    }

    public KlubJob(String id, Date actualJobExecutionTime, JobStatusEnum jobStatus,
                   JobTypeEnum jobType, Integer jobInterval, String resultLocation,
                   Integer currentRetires, Integer maxRetires, Date scheduledJobExecutionTime,
                   JobExecutionStatusEnum executionStatus, String payload) {
        super(id);
        this.actualJobExecutionTime = actualJobExecutionTime;
        this.jobStatus = jobStatus;
        this.jobType = jobType;
        this.jobInterval = jobInterval;
        this.resultLocation = resultLocation;
        this.currentRetires = currentRetires;
        this.maxRetires = maxRetires;
        this.scheduledJobExecutionTime = scheduledJobExecutionTime;
        this.executionStatus = executionStatus;
        this.payload = payload;
    }

    public Date getActualJobExecutionTime() {
        return actualJobExecutionTime;
    }

    public void setActualJobExecutionTime(Date actualJobExecutionTime) {
        this.actualJobExecutionTime = actualJobExecutionTime;
    }

    public JobStatusEnum getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(JobStatusEnum jobStatus) {
        this.jobStatus = jobStatus;
    }

    public JobTypeEnum getJobType() {
        return jobType;
    }

    public void setJobType(JobTypeEnum jobType) {
        this.jobType = jobType;
    }

    public Integer getJobInterval() {
        return jobInterval;
    }

    public void setJobInterval(Integer jobInterval) {
        this.jobInterval = jobInterval;
    }

    public String getResultLocation() {
        return resultLocation;
    }

    public void setResultLocation(String resultLocation) {
        this.resultLocation = resultLocation;
    }

    public Integer getCurrentRetires() {
        return currentRetires;
    }

    public void setCurrentRetires(Integer currentRetires) {
        this.currentRetires = currentRetires;
    }

    public Integer getMaxRetires() {
        return maxRetires;
    }

    public void setMaxRetires(Integer maxRetires) {
        this.maxRetires = maxRetires;
    }

    public Date getScheduledJobExecutionTime() {
        return scheduledJobExecutionTime;
    }

    public void setScheduledJobExecutionTime(Date scheduledJobExecutionTime) {
        this.scheduledJobExecutionTime = scheduledJobExecutionTime;
    }

    public JobExecutionStatusEnum getExecutionStatus() {
        return executionStatus;
    }

    public void setExecutionStatus(JobExecutionStatusEnum executionStatus) {
        this.executionStatus = executionStatus;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
