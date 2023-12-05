package com.natlex.sections.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@ToString
@Table(name = "asyncJobStatus")
@NoArgsConstructor
public class AsyncJobStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "jobStatus", nullable = false)
    private String jobStatus;

    public AsyncJobStatus(String value) {
        this.jobStatus = value;
    }
}
