package com.sankalpapp.dynamicProfile.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "results_pdf")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResultsPdf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(name = "pdf_link", nullable = false)
    private String pdfLink;
}