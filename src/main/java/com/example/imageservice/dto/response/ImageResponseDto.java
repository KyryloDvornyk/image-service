package com.example.imageservice.dto.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class ImageResponseDto {
    private Long id;
    private Long accountId;
    private Long size;
    private String reference;
    private String name;
    private String contentType;
    private LocalDateTime creationDateTime;
    private LocalDateTime lastUpdateDateTime;
    private List<String> tags = new ArrayList<>();
}
