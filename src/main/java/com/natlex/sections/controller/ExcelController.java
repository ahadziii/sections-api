package com.natlex.sections.controller;

import com.natlex.sections.service.AsyncJobService;
import com.natlex.sections.service.AsyncJobStatusService;
import com.natlex.sections.service.ExcelExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class ExcelController {

    private final AsyncJobService asyncJobService;
    private final AsyncJobStatusService asyncJobStatusService;
    private final ExcelExportService excelExportService;

    @PostMapping("/import")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        var result = asyncJobService.uploadFile(file).join();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/import/{id}")
    public ResponseEntity<String> getImportStatus(@PathVariable(name = "id") String id) {
        try {
            String status = asyncJobStatusService.getJobStatus(id);
            return ResponseEntity.ok(status);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/export")
    public ResponseEntity<String> downloadFile() throws IOException {
        var uuid = asyncJobService.initiateExport().join();
        return ResponseEntity.ok().body(uuid);
    }

    @GetMapping("/export/{id}")
    public ResponseEntity<String> getExportStatus(@PathVariable(name = "id") String id) {
        try {
            String status = asyncJobStatusService.getJobStatus(id);
            return ResponseEntity.ok(status);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/export/{id}/file")
    public ResponseEntity<?> getDownloadedFile(@PathVariable(name = "id") String id) throws IOException {

        if (Objects.equals(asyncJobStatusService.getJobStatus(id), "DONE")) {
            byte[] fileContent = excelExportService.downloadFile(id);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "workbook.xlsx");

            return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
        } else {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The job with ID " + id + " is still in progress");
        }
    }

}

