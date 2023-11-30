package com.natlex.sections.repository;

import com.natlex.sections.entity.GeologicalClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeologicalClassRepository extends JpaRepository<GeologicalClass, Long> {
}
