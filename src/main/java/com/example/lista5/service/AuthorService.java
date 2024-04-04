package com.example.lista5.service;

import com.example.lista5.dto.AuthorDTO;
import com.example.lista5.model.Author;
import com.example.lista5.repository.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class AuthorService implements IAuthorService {
    private final AuthorRepository authorRepository;
    public AuthorService(AuthorRepository authorRepository){
        this.authorRepository = authorRepository;
    }
    @Override
    @Cacheable("author")
    public AuthorDTO getAuthor(Long id){
        return authorRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Nie ma takiego autora w systemie")
        ).toDto();
    }
    @Override
    @Cacheable("authors")
    public List<AuthorDTO> getAuthors(){
        return authorRepository.findAll().stream().map(Author::toDto).toList();
    }
    @Override
    @CacheEvict(value = "authors",allEntries = true)
    public AuthorDTO addAuthor(Author author){
        author.setId(null);
        authorRepository.save(author);
        return author.toDto();
    }
    @Override
    @Caching( evict = {
            @CacheEvict(value = "authors",allEntries = true),
            @CacheEvict(value = "author",key = "#author.id")
    })
    public AuthorDTO updateAuthor(Author author){
        Author repAuthor = authorRepository.findById(author.getId()).orElseThrow(
                () -> new EntityNotFoundException("Nie ma takiego autora w systemie")
        );
        repAuthor.setFirstName(author.getFirstName());
        repAuthor.setLastName(author.getLastName());
        repAuthor.setCountry(author.getCountry());
        repAuthor.setBirthYear(author.getBirthYear());
        authorRepository.save(repAuthor);
        return repAuthor.toDto();
    }
    @Override
    @Caching( evict = {
            @CacheEvict(value = "authors",allEntries = true),
            @CacheEvict(value = "author",key = "#id")
    })
    public void deleteAuthor(Long id){
        authorRepository.delete(authorRepository.findById(id).orElseThrow());
    }
}
