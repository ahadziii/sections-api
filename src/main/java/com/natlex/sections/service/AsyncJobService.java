package com.natlex.sections.service;

import com.natlex.sections.entity.AsyncJobStatus;
import com.natlex.sections.entity.Section;
import com.natlex.sections.repository.AsyncJobStatusRepository;
import jakarta.servlet.ServletOutputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AsyncJobService {

    private final ExcelImportService excelImportService;
    private final ExcelExportService excelExportService;
    private final AsyncJobStatusRepository asyncJobStatusRepository;

    public void uploadFile(MultipartFile file) throws IOException {
        excelImportService.readExcelData(file);
    }

    public String downloadFile(ServletOutputStream outputStream) throws IOException {

        //TODO: update the status of the job when it is done using async
//        if (optionalAsyncJobStatus.isPresent()) {
//            AsyncJobStatus existingAsyncJobStatus = optionalAsyncJobStatus.get();
//            existingAsyncJobStatus.setJobStatus("DONE");
//            asyncJobStatusRepository.save(existingAsyncJobStatus);

        try {
            excelExportService.writeExcelData(outputStream);

            String uuid = generateUUID();

            var asyncJobStatus = AsyncJobStatus.builder()
                    .uuid(uuid)
                    .jobStatus("IN PROGRESS")
                    .build();

            asyncJobStatusRepository.save(asyncJobStatus);
            return uuid;
        } catch (Exception e) {
            return "Error initiating download: " + e.getMessage();
        }
    }

    private String generateUUID() {
        return UUID.randomUUID().toString();
    }
}
