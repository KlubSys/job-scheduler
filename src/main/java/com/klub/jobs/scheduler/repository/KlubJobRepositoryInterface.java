package com.klub.jobs.scheduler.repository;

import com.klub.jobs.scheduler.model.entity.KlubJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KlubJobRepositoryInterface extends JpaRepository<KlubJob, String> {
}
