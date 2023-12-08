package com.natlex.sections.service;

import com.natlex.sections.entity.GeologicalClass;
import com.natlex.sections.entity.Section;
import com.natlex.sections.repository.SectionRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcelExportService {

    private final SectionRepository sectionRepository;

    public void writeExcelData(OutputStream outputStream) throws IOException {

        var sections = sectionRepository.findAll();

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sections");

            int rowIndex = 0;

            for (Section section : sections) {
                Row row = sheet.createRow(rowIndex++);

                Cell cell = row.createCell(0);
                cell.setCellValue(section.getName());

                List<GeologicalClass> geologicalClasses = section.getGeologicalClasses();
                for (int i = 0; i < geologicalClasses.size(); i++) {
                    GeologicalClass geologicalClass = geologicalClasses.get(i);

                    row.createCell(2 * i + 1).setCellValue(geologicalClass.getName());
                    row.createCell(2 * i + 2).setCellValue(geologicalClass.getCode());
                }
            }
            workbook.write(outputStream);
        }
    }
}
