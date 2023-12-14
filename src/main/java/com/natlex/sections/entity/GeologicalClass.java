package com.natlex.sections.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;


@Entity
@Table(name = "geologicalClass")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GeologicalClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "code")
    @NotNull
    private String code;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "section_id")
    private Section section;

}

