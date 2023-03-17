package com.klub.jobs.scheduler.repository;

import com.klub.jobs.scheduler.model.entity.KlubScheduledJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface KlubScheduledJobRepositoryInterface extends JpaRepository<KlubScheduledJob, String> {

    //TODO the x size must match the delay in the scheduling
    @Query("SELECT j FROM KlubScheduledJob j " +
            "WHERE j. scheduledJobExecutionTime >= :start_date " +
            "AND (j.scheduledJobExecutionTime < :now_date) " +
            "AND j.shardId = :shard_id")
    List<KlubScheduledJob> getWidowsBucketJob(@Param("start_date") Date before,
                                              @Param("shard_id") Integer shardId,
                                              @Param("now_date") Date date);
}
