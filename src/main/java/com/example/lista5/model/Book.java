package com.example.lista5.model;

import com.example.lista5.dto.BookDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private Integer pages;
    private boolean rentFlag;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    public BookDTO toDto(){
        return new BookDTO(id,title,pages,rentFlag,author.getId());
    }
}
