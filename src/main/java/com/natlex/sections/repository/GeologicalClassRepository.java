package com.natlex.sections.repository;

import com.natlex.sections.entity.GeologicalClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeologicalClassRepository extends JpaRepository<GeologicalClass, Long> {
    List<GeologicalClass> findByCode(String code);
}
