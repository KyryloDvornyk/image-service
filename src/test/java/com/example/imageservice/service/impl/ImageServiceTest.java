package com.example.imageservice.service.impl;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import com.example.imageservice.exception.UnauthenticatedAccessException;
import com.example.imageservice.model.Account;
import com.example.imageservice.model.Image;
import com.example.imageservice.model.Tag;
import com.example.imageservice.repository.AccountRepository;
import com.example.imageservice.repository.ImageRepository;
import com.example.imageservice.repository.TagRepository;
import com.example.imageservice.service.FileUploaderService;
import com.example.imageservice.service.mapper.impl.ImageDtoMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootTest
class ImageServiceTest {
    @Mock
    private ImageRepository imageRepository;
    @Mock
    private ImageDtoMapper imageDtoMapper;
    @Mock
    private FileUploaderService fileUploaderService;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private TagRepository tagRepository;
    @InjectMocks
    private ImageServiceImpl imageService;
    @Captor
    ArgumentCaptor<Image> imageArgumentCaptor;

    @Test
    public void save_Ok() {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Principal principal = Mockito.mock(Principal.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(principal);
        Mockito.when(principal.toString()).thenReturn("principal");
        MockMultipartFile file = new MockMultipartFile("name",
                "originalName", "png", new byte[]{1, 2, 3});
        Mockito.when(accountRepository.findByLogin(Mockito.any()))
                .thenReturn(Optional.of(new Account()));
        Mockito.when(fileUploaderService.upload(Mockito.any())).thenReturn("reference");
        imageService.save(file);
        Mockito.verify(imageRepository).save(imageArgumentCaptor.capture());
        Assertions.assertEquals("originalName", imageArgumentCaptor.getValue().getName());
        Assertions.assertEquals("png", imageArgumentCaptor.getValue().getContentType());
        Assertions.assertEquals(3L, imageArgumentCaptor.getValue().getSize());
        Assertions.assertEquals("reference", imageArgumentCaptor.getValue().getReference());
    }

    @Test
    public void update_Ok() {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Principal principal = Mockito.mock(Principal.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(principal);
        Mockito.when(principal.toString()).thenReturn("principal");
        Account account = new Account();
        Image image = new Image();
        image.setId(1L);
        account.setImages(List.of(image));
        Mockito.when(accountRepository.findByLogin(Mockito.any())).thenReturn(Optional.of(account));
        Mockito.when(fileUploaderService.upload(Mockito.any())).thenReturn("reference");
        MockMultipartFile file = new MockMultipartFile("name",
                "originalName", "png", new byte[]{1, 2, 3});
        imageService.update(file, 1L);
        Mockito.verify(imageRepository).save(imageArgumentCaptor.capture());
        Assertions.assertEquals("originalName", imageArgumentCaptor.getValue().getName());
        Assertions.assertEquals("png", imageArgumentCaptor.getValue().getContentType());
        Assertions.assertEquals(3L, imageArgumentCaptor.getValue().getSize());
        Assertions.assertEquals("reference", imageArgumentCaptor.getValue().getReference());
        Assertions.assertEquals(1L, imageArgumentCaptor.getValue().getId());
    }

    @Test
    public void update_NotOk() {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Principal principal = Mockito.mock(Principal.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(principal);
        Mockito.when(principal.toString()).thenReturn("principal");
        Mockito.when(accountRepository.findByLogin(Mockito.any())).thenReturn(Optional.of(new Account()));
        MockMultipartFile file = new MockMultipartFile("name",
                "originalName", "png", new byte[]{1, 2, 3});
        Assertions.assertThrows(UnauthenticatedAccessException.class, () -> imageService.update(file, 1L));
    }

    @Test
    public void addTag_Ok() {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Principal principal = Mockito.mock(Principal.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(principal);
        Mockito.when(principal.toString()).thenReturn("principal");
        Account account = new Account();
        Image image = new Image();
        image.setId(1L);
        account.setImages(List.of(image));
        Mockito.when(accountRepository.findByLogin(Mockito.any())).thenReturn(Optional.of(account));
        Mockito.when(imageRepository.getById(1L)).thenReturn(image);
        Tag tag = new Tag();
        tag.setId(1L);
        tag.setName("tag");
        Mockito.when(tagRepository.getById(1L)).thenReturn(tag);
        imageService.addTag(1L, 1L);
        Mockito.verify(imageRepository).save(imageArgumentCaptor.capture());
        Assertions.assertEquals(1L, imageArgumentCaptor.getValue().getTags().get(0).getId());
        Assertions.assertEquals("tag", imageArgumentCaptor.getValue().getTags().get(0).getName());
    }

    @Test
    public void get_Ok() {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Principal principal = Mockito.mock(Principal.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(principal);
        Mockito.when(principal.toString()).thenReturn("principal");
        Account account = new Account();
        Image image = new Image();
        image.setId(1L);
        account.setImages(List.of(image));
        Mockito.when(accountRepository.findByLogin(Mockito.any())).thenReturn(Optional.of(account));
        imageService.get(1L);
    }

    @Test
    public void delete_Ok() {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Principal principal = Mockito.mock(Principal.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(principal);
        Mockito.when(principal.toString()).thenReturn("principal");
        Account account = new Account();
        Image image = new Image();
        image.setId(1L);
        account.setImages(List.of(image));
        Mockito.when(accountRepository.findByLogin(Mockito.any())).thenReturn(Optional.of(account));
        imageService.delete(1L);
    }
}