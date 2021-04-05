package com.books.books.rest;

import com.books.books.converters.AuthorConverterImpl;
import com.books.books.dto.AuthorDTO;
import com.books.books.repositoriesSpringDataJPA.AuthorRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AuthorController {
    private final AuthorRepository authorRepository;
    private final AuthorConverterImpl authorConverterImpl;

    public AuthorController(AuthorRepository authorRepository, AuthorConverterImpl authorConverterImpl) {
        this.authorRepository = authorRepository;
        this.authorConverterImpl = authorConverterImpl;
    }

    @GetMapping("/api/authors")
    public List<AuthorDTO> getAllAuthors() {
        return authorRepository.findAll().stream().map(authorConverterImpl::toDTO)
                .collect(Collectors.toList());
    }
}
