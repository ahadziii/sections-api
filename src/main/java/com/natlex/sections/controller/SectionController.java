package com.natlex.sections.controller;

import com.natlex.sections.dto.SectionDTO;
import com.natlex.sections.service.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sections")
@RequiredArgsConstructor
public class SectionController {

    private final SectionService sectionService;

    @GetMapping("")
    public ResponseEntity<List<SectionDTO>> getAllSections() {
        return ResponseEntity.ok().body(sectionService.getAllSections());
    }

    @GetMapping("/by-code")
    public ResponseEntity<List<SectionDTO>> getSectionByCode(@RequestParam(name = "code") String code) {
        return ResponseEntity.ok().body(sectionService.getSectionsByCode(code));
    }

}
