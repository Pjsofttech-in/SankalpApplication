package com.testapplication.dto.Response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DownloadResponse {

    private Long id;
    private String title;
    private String description;
    private String fileName;
    private String filePath;
    private Boolean active;
}