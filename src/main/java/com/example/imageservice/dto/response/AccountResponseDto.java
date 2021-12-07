package com.example.imageservice.dto.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class AccountResponseDto {
    private Long id;
    private String login;
    private LocalDateTime creationDateTime;
    private LocalDateTime lastUpdateDateTime;
    private List<Long> imageIds = new ArrayList<>();
}
