package com.natlex.sections.controller;

import com.natlex.sections.entity.Section;
import com.natlex.sections.service.AsyncJobService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ExcelController {

    @Autowired
    private final AsyncJobService asyncJobService;

    @PostMapping("/import")
    public void importFile(@RequestParam("file") MultipartFile file) throws IOException {
         asyncJobService.uploadFile(file);
    }

    @GetMapping("/export")
    public ResponseEntity<String> exportFile(HttpServletResponse response)  {

        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=workbook.xlsx");

            var uuid = asyncJobService.downloadFile(response.getOutputStream());
            return ResponseEntity.ok().body(uuid);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error initiating download.");
        }

    }

}

