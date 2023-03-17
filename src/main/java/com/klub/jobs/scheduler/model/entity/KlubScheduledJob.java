package com.klub.jobs.scheduler.model.entity;

import com.klub.jobs.scheduler.model.UuidAsIdEntity;

import javax.persistence.*;
import java.util.Date;

@Table(name = "klub_scheduled_job")
@Entity
public class KlubScheduledJob extends UuidAsIdEntity {

    //TODO scheduled_job_execution_time (partition key) | uuid |
    @Column(name = "scheduled_job_execution_time", columnDefinition = "timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date scheduledJobExecutionTime;
    //| shard_id (partition key)                     | int  |
    private Integer shardId;

    //| job_id (sort key)
    @JoinColumn(nullable = false)
    @ManyToOne
    private KlubJob job;

    public KlubScheduledJob() {
        super();
    }

    public KlubScheduledJob(String id, Date scheduledJobExecutionTime,
                            Integer shardId, KlubJob job) {
        super(id);
        this.scheduledJobExecutionTime = scheduledJobExecutionTime;
        this.shardId = shardId;
        this.job = job;
    }

    public Date getScheduledJobExecutionTime() {
        return scheduledJobExecutionTime;
    }

    public void setScheduledJobExecutionTime(Date scheduledJobExecutionTime) {
        this.scheduledJobExecutionTime = scheduledJobExecutionTime;
    }

    public Integer getShardId() {
        return shardId;
    }

    public void setShardId(Integer shardId) {
        this.shardId = shardId;
    }

    public KlubJob getJob() {
        return job;
    }

    public void setJob(KlubJob job) {
        this.job = job;
    }
}
