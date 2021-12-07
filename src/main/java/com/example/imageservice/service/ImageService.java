package com.example.imageservice.service;

import java.util.List;
import com.example.imageservice.dto.response.ImageResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService extends EntityService<ImageResponseDto, MultipartFile> {
    ImageResponseDto addTag(Long imageId, Long tagId);

    List<ImageResponseDto> saveBatch(List<MultipartFile> files);

    Page<ImageResponseDto> findAllByParameter(String parameter, Pageable pageable);
}
