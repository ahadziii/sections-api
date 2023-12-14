package com.natlex.sections.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GeologicalClassDTO {
    private String name;
    private String code;
    @JsonIgnore
    private SectionDTO section;
}
