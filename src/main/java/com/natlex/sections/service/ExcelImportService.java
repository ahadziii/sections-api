package com.natlex.sections.service;

import com.natlex.sections.entity.GeologicalClass;
import com.natlex.sections.entity.Section;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExcelImportService {
    private final ModelMapper modelMapper;

    private final SectionService sectionService;
    private final AsyncJobStatusService asyncJobStatusService;


    @Async
    public void readExcelData(MultipartFile file, String uuid) {

        try (InputStream inputStream = file.getInputStream(); XSSFWorkbook workbook = new XSSFWorkbook(inputStream);) {

            Sheet sheet = workbook.getSheetAt(0);
            var sections = new ArrayList<Section>();

            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }

                var sectionName = row.getCell(0).getStringCellValue();
                if (Objects.equals(sectionName, "")) {
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

                // Set the section for each geological class
                geologicalClasses.forEach(geoClass -> geoClass.setSection(section));

                section.setGeologicalClasses(geologicalClasses);
                sections.add(section);
            }

            // var test = sections.stream().map((element) -> modelMapper.map(element, Section.class)).toList();

            sectionService.batchInsert(sections);
            asyncJobStatusService.updateJobStatus(uuid, "DONE");

        } catch (Exception e) {
            asyncJobStatusService.updateJobStatus(uuid, "ERROR");
            log.error("An error occurred: {}", e.getMessage());
        }
    }


}
