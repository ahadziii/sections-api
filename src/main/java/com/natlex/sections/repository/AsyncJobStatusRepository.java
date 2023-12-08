package com.natlex.sections.repository;

import com.natlex.sections.entity.AsyncJobStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AsyncJobStatusRepository extends JpaRepository<AsyncJobStatus, Long> {
    Optional<AsyncJobStatus> findByUuid(String uuid);
}
