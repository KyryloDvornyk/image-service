package com.example.imageservice.controller;

import java.util.List;
import com.example.imageservice.dto.response.ImageResponseDto;
import com.example.imageservice.service.ImageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/images")
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping
    public List<ImageResponseDto> create(@RequestPart List<MultipartFile> files) {
        return imageService.saveBatch(files);
    }

    @GetMapping("/{id}")
    public ImageResponseDto read(@PathVariable Long id) {
        return imageService.get(id);
    }

    @GetMapping
    public Page<ImageResponseDto> readAllByParameter(@RequestParam String parameter, Pageable pageable) {
        return imageService.findAllByParameter(parameter, pageable);
    }

    @PutMapping("/{id}")
    public ImageResponseDto update(@PathVariable Long id, @RequestPart MultipartFile file) {
        return imageService.update(file, id);
    }

    @PutMapping("/{imageId}/tags")
    public ImageResponseDto addTag(@PathVariable Long imageId, @RequestParam Long tagId) {
        return imageService.addTag(imageId, tagId);
    }

    @DeleteMapping("/{id}")
    public void update(@PathVariable Long id) {
        imageService.delete(id);
    }
}
