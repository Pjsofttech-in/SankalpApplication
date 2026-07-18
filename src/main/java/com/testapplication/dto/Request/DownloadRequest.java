package com.testapplication.dto.Request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DownloadRequest {

    private String title;
    private String description;
    private String fileName;
    private String filePath;
    private Boolean active;
}