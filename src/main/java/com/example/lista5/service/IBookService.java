package com.example.lista5.service;

import com.example.lista5.dto.BookDTO;

import java.util.Collection;

public interface IBookService {
    public BookDTO getBook(Long id);

    public Collection<BookDTO> getBooks();

    public BookDTO addBook(BookDTO book);

    public BookDTO updateBook(BookDTO book);

    public void deleteBook(Long id);
}
