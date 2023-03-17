package com.klub.jobs.scheduler.service.worker;

import com.klub.jobs.scheduler.helper.JobStatusEnum;
import com.klub.jobs.scheduler.helper.utils.DateOperationUtils;
import com.klub.jobs.scheduler.model.entity.KlubJob;
import com.klub.jobs.scheduler.model.entity.KlubScheduledJob;
import com.klub.jobs.scheduler.repository.KlubScheduledJobRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class JobReaderScheduler {

    private Integer shardId = 1;

    private final KlubScheduledJobRepositoryInterface scheduledJobRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ConcurrentHashMap<String, KlubScheduledJob> memoScheduledJobs =
            new ConcurrentHashMap<>(20);
    @Autowired
    public JobReaderScheduler(KlubScheduledJobRepositoryInterface scheduledJobRepository,
                              KafkaTemplate<String, String> kafkaTemplate) {
        this.scheduledJobRepository = scheduledJobRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedRate = 5000, initialDelay = 3000)
    void handleJobs(){
        System.out.println("Reading Jobs");

        Date currentDate = new Date();
        Date beforeDate = DateOperationUtils.subsSeconds(currentDate, 30);
        List<KlubScheduledJob> scheduledJobs = scheduledJobRepository.getWidowsBucketJob(
                beforeDate, shardId, currentDate);

        scheduledJobs.forEach(scheduledJob -> {
            KlubJob job = scheduledJob.getJob();

            //TODO review this point
            //if (job.getJobStatus() != JobStatusEnum.NOT_STARTED) return;
            //We have already handle this job
            //if (memoScheduledJobs.get(scheduledJob.getId()) != null) return;

            kafkaTemplate.send("file_decomposition", scheduledJob.getJob().getPayload())
                    .addCallback((result -> {
                        System.out.println("Message sent to kefka");

                        //Memorize the
                        memoScheduledJobs.replace(scheduledJob.getId(), scheduledJob);
                        //TODO remove elders from the queu
                    }), ex -> {
                        //TODO hANDLE
                        System.err.println(ex.getMessage());
                    });
        });

        System.out.println("Total Jobs fetched " + scheduledJobs.size());
    }
}
