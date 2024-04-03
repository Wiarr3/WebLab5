package com.example.lista5.service;

import com.example.lista5.dto.AuthorDTO;
import com.example.lista5.dto.BookDTO;
import com.example.lista5.model.Author;

import java.util.Collection;

public interface IAuthorService {
    public AuthorDTO getAuthor(Long id);

    public Collection<AuthorDTO> getAuthors();

    public AuthorDTO addAuthor(Author author);

    public AuthorDTO updateAuthor(Author author);

    public void deleteAuthor(Long id);
}
