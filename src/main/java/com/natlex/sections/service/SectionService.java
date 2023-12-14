package com.natlex.sections.service;

import com.natlex.sections.dto.SectionDTO;
import com.natlex.sections.entity.GeologicalClass;
import com.natlex.sections.entity.Section;
import com.natlex.sections.repository.GeologicalClassRepository;
import com.natlex.sections.repository.SectionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SectionService {

    private final SectionRepository sectionRepository;
    private final GeologicalClassRepository geologicalClassRepository;
    private final ModelMapper modelMapper;

    public List<SectionDTO> getAllSections() {
        return sectionRepository.findAll().stream()
                .map((element) -> modelMapper.map(element, SectionDTO.class)).collect(Collectors.toList());
    }

    public List<SectionDTO> getSectionsByCode(String code) {
        return geologicalClassRepository.findByCode(code).stream()
                .map(GeologicalClass::getSection)
                .map((element) -> modelMapper.map(element, SectionDTO.class)).collect(Collectors.toList());
    }

    @Transactional
    public void batchInsert(List<Section> sections) {
       sectionRepository.saveAll(sections);
    }

}
