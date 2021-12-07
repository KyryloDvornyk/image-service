package com.example.imageservice.service;

public interface EntityService<T, D> {
    T save(D requestDto);

    T update(D requestDto, Long id);

    T get(Long id);

    void delete(Long id);
}
