package com.natlex.sections.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AsyncJobStatusDTO {
    private String uuid;
    private String jobStatus;
}
