package com.natlex.sections.service;

import com.natlex.sections.entity.AsyncJobStatus;
import com.natlex.sections.repository.AsyncJobStatusRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AsyncJobStatusService {

    private final AsyncJobStatusRepository asyncJobStatusRepository;
    private final ModelMapper modelMapper;

    public void saveAsyncJobStatus(AsyncJobStatus asyncJobStatus) {
        asyncJobStatusRepository.save(asyncJobStatus);
    }

    public void updateJobStatus(String uuid, String newStatus) {
        Optional<AsyncJobStatus> optionalAsyncJobStatus = findByUuid(uuid);
        optionalAsyncJobStatus.ifPresent(asyncJobStatus -> {
            asyncJobStatus.setJobStatus(newStatus);
            asyncJobStatusRepository.save(asyncJobStatus);
        });
    }

    public Optional<AsyncJobStatus> findByUuid(String uuid){
        return asyncJobStatusRepository.findByUuid(uuid);
    }

    public String getJobStatus(String uuid) {
        Optional<AsyncJobStatus> optionalAsyncJobStatus = findByUuid(uuid);
        return optionalAsyncJobStatus.map(AsyncJobStatus::getJobStatus).orElseThrow(NoSuchElementException::new);
    }

}
