package com.natlex.sections.service;

import com.natlex.sections.entity.AsyncJobStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class AsyncJobService {

    private final ExcelImportService excelImportService;
    private final ExcelExportService excelExportService;
    private final AsyncJobStatusService asyncJobStatusService;


    @Async
    public CompletableFuture<String> uploadFile(MultipartFile file) {
        String uuid = generateUUID();

        var jobStatus = AsyncJobStatus.builder()
                .jobStatus("IN PROGRESS")
                .uuid(uuid)
                .build();

        asyncJobStatusService.saveAsyncJobStatus(jobStatus);
        excelImportService.readExcelData(file, uuid);

        return CompletableFuture.completedFuture(uuid);
    }

    @Async
    public CompletableFuture<String> initiateExport() {

        var uuid = generateUUID();

        var jobStatus = AsyncJobStatus.builder()
                .jobStatus("IN PROGRESS")
                .uuid(uuid)
                .build();

        try {
            asyncJobStatusService.saveAsyncJobStatus(jobStatus);
            excelExportService.exportData(uuid);
        } catch (Exception e) {
            asyncJobStatusService.updateJobStatus(uuid, "ERROR");
            log.error("An error occurred: {}", e.getMessage());
        }

        return CompletableFuture.completedFuture(uuid);

    }



    private String generateUUID() {
        return UUID.randomUUID().toString();
    }
}
