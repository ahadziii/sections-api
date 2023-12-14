package com.natlex.sections.service;

import com.natlex.sections.entity.GeologicalClass;
import com.natlex.sections.entity.Section;
import com.natlex.sections.repository.GeologicalClassRepository;
import com.natlex.sections.repository.SectionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SectionService {

    private final SectionRepository sectionRepository;
    private final GeologicalClassRepository geologicalClassRepository;
    private final ModelMapper modelMapper;

    public List<Section> getAllSections() {
        return sectionRepository.findAll();

//        return sections.stream().map((element) -> modelMapper.map(element, SectionDto.class)).toList();
    }

    public List<Section> getSectionsByCode(String code) {
        return geologicalClassRepository.findByCode(code).stream()
                .map(GeologicalClass::getSection)
                .toList();

//        return sections.stream().map((element) -> modelMapper.map(element, SectionDto.class)).collect(Collectors.toList());
    }

    @Transactional
    public void batchInsert(List<Section> sections) {
       sectionRepository.saveAll(sections);
    }

}
