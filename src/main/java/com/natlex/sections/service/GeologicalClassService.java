package com.natlex.sections.service;

import com.natlex.sections.entity.GeologicalClass;
import com.natlex.sections.repository.GeologicalClassRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeologicalClassService {

    private final GeologicalClassRepository geologicalClassRepository;

    public GeologicalClassService(GeologicalClassRepository geologicalClassRepository) {
        this.geologicalClassRepository = geologicalClassRepository;
    }

    public List<GeologicalClass> geologicalClasses(){
        return geologicalClassRepository.findAll();
    }
}
