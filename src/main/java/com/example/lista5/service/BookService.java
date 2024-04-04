package com.example.lista5.service;

import com.example.lista5.dto.BookDTO;
import com.example.lista5.model.Author;
import com.example.lista5.model.Book;
import com.example.lista5.repository.AuthorRepository;
import com.example.lista5.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService implements IBookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository){
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    @Cacheable("book")
    public BookDTO getBook(Long id){
        return bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Nie ma takiej ksiazki w systemie")
        ).toDto();
    }
    @Override
    @Cacheable("books")
    public List<BookDTO> getBooks() {
        return bookRepository.findAll().stream().map(Book::toDto).toList();
    }
    @Override
    @CacheEvict(value = "books", allEntries = true)
    public BookDTO addBook(BookDTO book){
        book.setId(null);
        Author author = authorRepository.findById(book.getAuthor().getId()).orElseThrow(
                () -> new EntityNotFoundException("Autor nie jest wpisany do systemu")
        );
        Book repBook = new Book(book.getId(),book.getTitle(),book.getPages(), book.getRented(), author);
        bookRepository.save(repBook);
        return repBook.toDto();
    }
    @Override
    @Caching(evict = {
            @CacheEvict(value = "books", allEntries = true),
            @CacheEvict(value = "book", key = "#book.id")
    })
    public BookDTO updateBook(BookDTO book){
        Book repBook = bookRepository.findById(book.getId()).orElseThrow(
                () -> new EntityNotFoundException("Nie ma takiej ksiazki w systemie")
        );
        repBook.setTitle(book.getTitle());
        repBook.setAuthor(authorRepository.findById(book.getAuthor().getId()).orElseThrow(
                () -> new EntityNotFoundException("Autor nie jest wpisany do systemu")
        ));
        repBook.setPages(book.getPages());
        bookRepository.save(repBook);
        return repBook.toDto();
    }
    @Override
    @Caching(evict = {
            @CacheEvict(value = "books", allEntries = true),
            @CacheEvict(value = "book", key = "#book.id")
    })
    public void deleteBook(Long id){
        bookRepository.deleteById(id);
    }
}
