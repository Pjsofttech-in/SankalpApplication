package com.sankalpapp.dynamicProfile.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "marquee")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Marquee {

    @Id
    private Long id = 1L;

    @Column(nullable = false, length = 500)
    private String name;
}