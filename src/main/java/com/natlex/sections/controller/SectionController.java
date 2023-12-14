package com.natlex.sections.controller;

import com.natlex.sections.entity.Section;
import com.natlex.sections.service.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sections")
@RequiredArgsConstructor
public class SectionController {

    private final SectionService sectionService;

    @GetMapping("")
    public ResponseEntity<List<Section>> getAllSections() {
        return ResponseEntity.ok().body(sectionService.getAllSections());
    }

    @GetMapping("/by-code")
    public ResponseEntity<List<Section>> getSectionByCode(@RequestParam(name = "code") String code) {
        return ResponseEntity.ok().body(sectionService.getSectionsByCode(code));
    }

}
