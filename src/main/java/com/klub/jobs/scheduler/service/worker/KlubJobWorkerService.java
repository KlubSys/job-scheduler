package com.klub.jobs.scheduler.service.worker;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class KlubJobWorkerService {

    @Scheduled(fixedRate = 5000, initialDelay = 10000)
    void healthSignalUpdate(){
        System.out.println("Health");
    }
}
