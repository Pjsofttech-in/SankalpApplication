package com.sankalpapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "sections")
@Data
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    private boolean active = true;

    private LocalDateTime createdAt = LocalDateTime.now();

   // @ManyToOne
    //@JoinColumn(name = "subject_id", nullable = false)
    //private Subject subject;
}
