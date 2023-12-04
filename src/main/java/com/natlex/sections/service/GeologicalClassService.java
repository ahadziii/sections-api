package com.natlex.sections.service;

import com.natlex.sections.entity.GeologicalClass;
import com.natlex.sections.repository.GeologicalClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeologicalClassService {

    private final GeologicalClassRepository geologicalClassRepository;

    public List<GeologicalClass> geologicalClasses(){
        return geologicalClassRepository.findAll();
    }
}
