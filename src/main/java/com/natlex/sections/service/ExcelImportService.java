package com.natlex.sections.service;

import com.natlex.sections.dto.GeologicalClassDTO;
import com.natlex.sections.dto.SectionDTO;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExcelImportService {
    private final ModelMapper modelMapper;

    private final SectionService sectionService;
    private final AsyncJobStatusService asyncJobStatusService;


    @Async
    public void readExcelData(MultipartFile file, String uuid) {

        try (InputStream inputStream = file.getInputStream(); XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            var sections = new ArrayList<SectionDTO>();

            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }

                var sectionName = row.getCell(0).getStringCellValue();
                if (Objects.equals(sectionName, "")) {
                    break;
                }

                var geologicalClasses = new ArrayList<GeologicalClassDTO>();

                for (int i = 1; i < row.getPhysicalNumberOfCells(); i += 2) {
                    var className = row.getCell(i).getStringCellValue();
                    var classCode = row.getCell(i + 1).getStringCellValue();

                    var geologicalClass = new GeologicalClassDTO();
                    geologicalClass.setName(className);
                    geologicalClass.setCode(classCode);
                    geologicalClasses.add(geologicalClass);

                }

                var section = new SectionDTO();
                section.setName(sectionName);

                // Set the section for each geological class
                geologicalClasses.forEach(geoClass -> geoClass.setSection(section));

                section.setGeologicalClasses(geologicalClasses);
                sections.add(section);
            }

            sectionService.batchInsert(sections.stream()
                    .map((element) -> modelMapper.map(element, Section.class)).collect(Collectors.toList()));
            asyncJobStatusService.updateJobStatus(uuid, "DONE");

        } catch (Exception e) {
            asyncJobStatusService.updateJobStatus(uuid, "ERROR");
            log.error("An error occurred: {}", e.getMessage());
        }
    }


}
