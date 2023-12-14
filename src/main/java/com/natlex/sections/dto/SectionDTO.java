package com.natlex.sections.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SectionDTO {
    private String name;
    private List<GeologicalClassDTO> geologicalClasses;
}
