package com.example.imageservice.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploaderService {
    String upload(MultipartFile file);
}
