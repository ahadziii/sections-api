package com.natlex.sections.service;

import com.natlex.sections.entity.GeologicalClass;
import com.natlex.sections.entity.Section;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ExcelImportService {

    @Async
    public List<Section> readExcelData(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

        var sheet = workbook.getSheetAt(0);
        var sections = new ArrayList<Section>();

        for (var row : sheet) {
            if (row.getRowNum() == 0) {

                continue;
            }

            var sectionName = row.getCell(0).getStringCellValue();
            if(Objects.equals(sectionName, "")){
                break;
            }
            var geologicalClasses = new ArrayList<GeologicalClass>();

            for (int i = 1; i < row.getPhysicalNumberOfCells(); i += 2) {
                var className = row.getCell(i).getStringCellValue();
                var classCode = row.getCell(i + 1).getStringCellValue();

                var geologicalClass = new GeologicalClass();
                geologicalClass.setName(className);
                geologicalClass.setCode(classCode);
                geologicalClasses.add(geologicalClass);
            }

            var section = new Section();
            section.setName(sectionName);
            section.setGeologicalClasses(geologicalClasses);
            sections.add(section);
        }

        return sections;
    }
}
