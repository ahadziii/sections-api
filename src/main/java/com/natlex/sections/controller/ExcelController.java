package com.natlex.sections.controller;

import com.natlex.sections.entity.Section;
import com.natlex.sections.service.AsyncJobService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ExcelController {

    @Autowired
    private final AsyncJobService asyncJobService;

    @PostMapping("/import")
    public List<Section> importFile(@RequestParam("file") MultipartFile file) throws IOException {
        return asyncJobService.uploadFile(file);
    }

}

