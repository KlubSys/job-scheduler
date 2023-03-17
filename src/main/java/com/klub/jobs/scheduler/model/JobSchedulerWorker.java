package com.klub.jobs.scheduler.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobSchedulerWorker {

    private String id;
    private String ipAddress;
    private Integer shardId;
}
