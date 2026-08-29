package com.sankalpapp.dynamicProfile.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WebCounter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "counter_name_1", nullable = false)
    private String counterName1;

    @Column(name = "count_value_1", nullable = false)
    private int countValue1;

    @Column(name = "counter_color_1")
    private String counterColor1;

    @Column(name = "counter_name_2", nullable = false)
    private String counterName2;

    @Column(name = "count_value_2", nullable = false)
    private int countValue2;

    @Column(name = "counter_color_2")
    private String counterColor2;

    @Column(name = "counter_name_3", nullable = false)
    private String counterName3;

    @Column(name = "count_value_3", nullable = false)
    private int countValue3;

    @Column(name = "counter_color_3")
    private String counterColor3;
    private String url;


    @ManyToOne
    @JoinColumn(name = "security_url_id")
    @JsonIgnore
    private WebSecurityUrl webSecurityUrl;
}
