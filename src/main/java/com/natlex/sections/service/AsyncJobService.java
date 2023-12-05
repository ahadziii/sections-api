package com.natlex.sections.service;

import com.natlex.sections.entity.Section;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AsyncJobService {

    private final ExcelImportService excelImportService;

    public List<Section> uploadFile(MultipartFile file) throws IOException {
        return excelImportService.readExcelData(file);
    }

}
