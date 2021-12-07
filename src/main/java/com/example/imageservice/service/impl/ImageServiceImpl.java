package com.example.imageservice.service.impl;

import java.util.ArrayList;
import java.util.List;
import com.example.imageservice.dto.response.ImageResponseDto;
import com.example.imageservice.exception.UnauthenticatedAccessException;
import com.example.imageservice.model.Account;
import com.example.imageservice.model.Image;
import com.example.imageservice.model.Tag;
import com.example.imageservice.repository.AccountRepository;
import com.example.imageservice.repository.ImageRepository;
import com.example.imageservice.repository.TagRepository;
import com.example.imageservice.service.FileUploaderService;
import com.example.imageservice.service.ImageService;
import com.example.imageservice.service.mapper.impl.ImageDtoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final ImageDtoMapper imageDtoMapper;
    private final FileUploaderService fileUploaderService;
    private final AccountRepository accountRepository;
    private final TagRepository tagRepository;

    public ImageServiceImpl(ImageRepository imageRepository, ImageDtoMapper imageDtoMapper,
                            FileUploaderService fileUploaderService, AccountRepository accountRepository,
                            TagRepository tagRepository) {
        this.imageRepository = imageRepository;
        this.imageDtoMapper = imageDtoMapper;
        this.fileUploaderService = fileUploaderService;
        this.accountRepository = accountRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public ImageResponseDto save(MultipartFile file) {
        Image image = mapImage(file);
        return imageDtoMapper.mapToDto(imageRepository.save(image));
    }

    @Override
    public ImageResponseDto update(MultipartFile file, Long id) {
        isImageInAccount(id);
        Image image = mapImage(file);
        image.setId(id);
        return imageDtoMapper.mapToDto(imageRepository.save(image));
    }

    @Override
    public ImageResponseDto addTag(Long imageId, Long tagId) {
        isImageInAccount(imageId);
        Image image = imageRepository.getById(imageId);
        Tag tag = tagRepository.getById(tagId);
        image.getTags().add(tag);
        return imageDtoMapper.mapToDto(imageRepository.save(image));
    }

    @Override
    public List<ImageResponseDto> saveBatch(List<MultipartFile> files) {
        List<ImageResponseDto> dtos = new ArrayList<>();
        for (MultipartFile file : files) {
            dtos.add(save(file));
        }
        return dtos;
    }

    @Override
    public Page<ImageResponseDto> findAllByParameter(String parameter, Pageable pageable) {
        Page<Image> images = imageRepository.findAllByParameter(parameter, pageable);
        return images.map(imageDtoMapper::mapToDto);
    }

    @Override
    public ImageResponseDto get(Long id) {
        isImageInAccount(id);
        return imageDtoMapper.mapToDto(imageRepository.getById(id));
    }

    @Override
    public void delete(Long id) {
        isImageInAccount(id);
        imageRepository.deleteById(id);
    }

    private Image mapImage(MultipartFile file) {
        Image image = new Image();
        image.setAccount(accountRepository.findByLogin(getCurrentUsername()).orElseThrow());
        image.setContentType(file.getContentType());
        image.setName(file.getOriginalFilename());
        image.setSize(file.getSize());
        image.setReference(fileUploaderService.upload(file));
        return image;
    }

    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    private void isImageInAccount(Long id) {
        Account account = accountRepository.findByLogin(getCurrentUsername()).orElseThrow();
        if (account.getImages().stream().noneMatch(i -> i.getId().equals(id))) {
            throw new UnauthenticatedAccessException("Account has no any image with id " + id);
        }
    }
}
