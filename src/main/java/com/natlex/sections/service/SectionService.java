package com.natlex.sections.service;

import com.natlex.sections.entity.Section;
import com.natlex.sections.repository.SectionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectionService {

    private final SectionRepository sectionRepository;

    public SectionService(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    public List<Section> getAllSections(){
        return sectionRepository.findAll();
    }
}
