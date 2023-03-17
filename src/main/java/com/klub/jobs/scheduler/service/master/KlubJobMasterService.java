package com.klub.jobs.scheduler.service.master;

import com.klub.jobs.scheduler.exception.ForbiddenException;
import com.klub.jobs.scheduler.model.JobSchedulerWorker;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class KlubJobMasterService {

    private ConcurrentHashMap<String, JobSchedulerWorker> workers = new ConcurrentHashMap<>();
    private Integer nextShardId = 1;

    public JobSchedulerWorker registerWorker(String id, String ipAddress) throws ForbiddenException {
        JobSchedulerWorker worker = new JobSchedulerWorker();
        worker.setShardId(nextShardId);
        worker.setId(id);
        worker.setIpAddress(ipAddress);

        if (workers.get(id) != null) throw new ForbiddenException("Id of worker already exists");

        workers.put(id, worker);
        nextShardId = nextShardId + 1;

        return worker;
    }

    public ConcurrentHashMap<String, JobSchedulerWorker> getWorkers() {
        return workers;
    }

    public Integer getNextShardId() {
        return nextShardId;
    }
}
