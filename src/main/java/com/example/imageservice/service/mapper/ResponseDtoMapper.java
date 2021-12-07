package com.example.imageservice.service.mapper;

public interface ResponseDtoMapper<T, D> {
    T mapToDto(D model);
}
