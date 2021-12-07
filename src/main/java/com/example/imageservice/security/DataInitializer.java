package com.example.imageservice.security;

import com.example.imageservice.model.Tag;
import com.example.imageservice.repository.TagRepository;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

@Component
public class DataInitializer {
    private final TagRepository tagRepository;

    public DataInitializer(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @PostConstruct
    public void inject() {
        Tag tag1 = new Tag();
        tag1.setName("tag1");
        tagRepository.save(tag1);
        Tag tag2 = new Tag();
        tag2.setName("tag2");
        tagRepository.save(tag2);
        Tag tag3 = new Tag();
        tag3.setName("tag3");
        tagRepository.save(tag3);
    }
}
