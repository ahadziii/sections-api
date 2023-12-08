package com.natlex.sections.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@ToString
@Table(name = "asyncJobStatus")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AsyncJobStatus {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "uuid", nullable = false)
    private String uuid;

    @Column(name = "jobStatus", nullable = false)
    private String jobStatus;

}
