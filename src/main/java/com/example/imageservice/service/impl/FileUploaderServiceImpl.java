package com.example.imageservice.service.impl;

import com.example.imageservice.service.FileUploaderService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploaderServiceImpl implements FileUploaderService {
    @Override
    public String upload(MultipartFile file) {
        return "https://some.domain.name/" + file.getOriginalFilename();
    }
}
