package com.books.books.rest;

import com.books.books.dto.AuthorDTO;
import com.books.books.repositoriesSpringDataJPA.AuthorRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AuthorController {
    private final AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping("/api/authors")
    public List<AuthorDTO> getAllAuthors() {
        return authorRepository.findAll().stream().map(AuthorDTO::toDTO)
                .collect(Collectors.toList());
    }
}
