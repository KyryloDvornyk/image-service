package com.example.imageservice.service.mapper.impl;

import java.util.stream.Collectors;
import com.example.imageservice.dto.response.ImageResponseDto;
import com.example.imageservice.model.Image;
import com.example.imageservice.model.Tag;
import com.example.imageservice.service.mapper.ResponseDtoMapper;
import org.springframework.stereotype.Component;

@Component
public class ImageDtoMapper implements ResponseDtoMapper<ImageResponseDto, Image> {
    @Override
    public ImageResponseDto mapToDto(Image model) {
        ImageResponseDto dto = new ImageResponseDto();
        dto.setAccountId(model.getAccount().getId());
        dto.setContentType(model.getContentType());
        dto.setId(model.getId());
        dto.setSize(model.getSize());
        dto.setName(model.getName());
        dto.setReference(model.getReference());
        dto.setCreationDateTime(model.getCreationDateTime());
        dto.setLastUpdateDateTime(model.getLastUpdateDateTime());
        dto.setTags(model.getTags().stream()
                .map(Tag::getName)
                .collect(Collectors.toList()));
        return dto;
    }
}
