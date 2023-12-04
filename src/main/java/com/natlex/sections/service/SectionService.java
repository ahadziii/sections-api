package com.natlex.sections.service;

import com.natlex.sections.entity.GeologicalClass;
import com.natlex.sections.entity.Section;
import com.natlex.sections.repository.GeologicalClassRepository;
import com.natlex.sections.repository.SectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SectionService {

    private final SectionRepository sectionRepository;
    private final GeologicalClassRepository geologicalClassRepository;

    public List<Section> getAllSections(){
        return sectionRepository.findAll();
    }

    public List<Section> getSectionsByCode(String code){
        return geologicalClassRepository.findByCode(code).stream()
                .map(GeologicalClass::getSection)
                .collect(Collectors.toList());
    }

}
