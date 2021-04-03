package com.books.books.rest;

import com.books.books.dto.StyleDTO;
import com.books.books.repositoriesSpringDataJPA.StyleRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class StyleController {
    private final StyleRepository styleRepository;

    public StyleController(StyleRepository styleRepository) {
        this.styleRepository = styleRepository;
    }

    @GetMapping("/api/styles")
    public List<StyleDTO> getAllStyles() {
        return styleRepository.findAll().stream().map(StyleDTO::toDTO)
                .collect(Collectors.toList());
    }
}
