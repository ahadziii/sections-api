package com.natlex.sections.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JobStatus {
    DONE("Done"),
    IN_PROGRESS("In Progress"),
    ERROR("Error");

    private final String value;

}

