package com.example.imageservice.service.mapper;

public interface RequestDtoMapper<T, D> {
    T mapToModel(D dto);
}
