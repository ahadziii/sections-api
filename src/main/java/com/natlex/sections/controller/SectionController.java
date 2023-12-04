package com.natlex.sections.controller;

import com.natlex.sections.entity.Section;
import com.natlex.sections.service.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sections")
@RequiredArgsConstructor
public class SectionController {

    private final SectionService sectionService;

    @GetMapping("")
    public List<Section> getAllSections() {
        return sectionService.getAllSections();
    }

    @GetMapping("/by-code")
    public List<Section> getSectionByCode(@RequestParam(name = "code") String code) {
        return sectionService.getSectionsByCode(code);
    }

}
