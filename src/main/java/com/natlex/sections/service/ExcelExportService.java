package com.natlex.sections.service;

import com.natlex.sections.entity.Section;
import com.natlex.sections.repository.SectionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExcelExportService {
    private final ModelMapper modelMapper;

    private final SectionRepository sectionRepository;
    private final AsyncJobStatusService asyncJobStatusService;

    @Async
    public void exportData(String uuid) throws IOException {

        var sections = sectionRepository.findAll();
        var tempDirectory = System.getProperty("java.io.tmpdir");
        var filePath = Paths.get(tempDirectory, "exportedFile_" + uuid + ".xlsx");

        var maxGeologicalClassCount = getMaxGeologicalClassCount(sections);

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sections");

            addHeaders(sheet, maxGeologicalClassCount);

            var rowIndex = 1;

            for (var section : sections) {
                var row = sheet.createRow(rowIndex++);

                var cell = row.createCell(0);
                cell.setCellValue(section.getName());

                var geologicalClasses = section.getGeologicalClasses();
                for (int i = 0; i < geologicalClasses.size(); i++) {
                    var geologicalClassDto = geologicalClasses.get(i);

                    row.createCell(2 * i + 1).setCellValue(geologicalClassDto.getName());
                    row.createCell(2 * i + 2).setCellValue(geologicalClassDto.getCode());
                }
            }

            //write data and save it to tempLocation
            try (OutputStream outputStream = Files.newOutputStream(filePath)) {
                workbook.write(outputStream);
            }

            asyncJobStatusService.updateJobStatus(uuid, "DONE");
        }
    }

    public byte[] downloadFile(String uuid) throws IOException {
        var tempDirectory = System.getProperty("java.io.tmpdir");
        var filePath = Paths.get(tempDirectory, "exportedFile_" + uuid + ".xlsx");

        var status = asyncJobStatusService.getJobStatus(uuid);

        if (Objects.equals(status, "DONE")) {
            return Files.readAllBytes(filePath);
        } else {
            throw new RuntimeException("Export not available for ID: " + uuid);
        }
    }


    private Integer getMaxGeologicalClassCount(List<Section> sections) {
        return sections.stream()
                .mapToInt(section -> section.getGeologicalClasses().size())
                .max()
                .orElse(0);
    }

    private void addHeaders(Sheet sheet, int maxGeologicalClassCount) {

        var rowIndex = 0;
        var row = sheet.createRow(rowIndex++);
        var cell = row.createCell(0);
        cell.setCellValue("Section Name");

        for (int i = 0; i < maxGeologicalClassCount; i++) {
            row.createCell(2 * i + 1).setCellValue("Class " + (1 + i) + " Name");
            row.createCell(2 * i + 2).setCellValue("Class " + (1 + i) + " Code");
        }
    }

}


