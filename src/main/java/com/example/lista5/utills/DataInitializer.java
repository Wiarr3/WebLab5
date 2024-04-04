package com.example.lista5.utills;
import com.example.lista5.model.Author;
import com.example.lista5.model.Book;
import com.example.lista5.repository.AuthorRepository;
import com.example.lista5.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Configuration
public class DataInitializer {
    private static final String[] FIRST_NAMES = {"Jan", "Anna", "Piotr", "Maria", "Krzysztof", "Katarzyna", "Andrzej", "Agnieszka", "Tomasz", "Barbara", "Marcin", "Magdalena", "Grzegorz", "Aleksandra", "Michał"};
    private static final String[] LAST_NAMES = {"Nowak", "Kowalska", "Wiśniewski", "Dąbrowska", "Lewandowski", "Wójcik", "Kamiński", "Zielińska", "Szymański", "Woźniak"};
    private static final String[] BOOK_TITLES = {
            "Tajemnice Kosmosu", "Głębia Oceanu", "Zaginione Cywilizacje", "Wulkany Świata", "Historia Bez Tajemnic",
            "Podróże Marzeń", "Sekrety Technologii", "Świat Przyrody", "Cuda Architektury", "Mistrzowie Sztuki",
            "Innowacje Przyszłości", "Tajemnice Umysłu", "Kuchnie Świata", "Muzyka i Emocje", "Filozofia Życia",
            "Astronomia dla Początkujących", "Podstawy Programowania", "Niezwykłe Zwierzęta", "Ekosystemy Ziemi", "Wspaniałe Wynalazki"
    };

    @Bean
    CommandLineRunner initDatabase(AuthorRepository authorRepository, BookRepository bookRepository) {
        return args -> {
            List<Author> authors = new ArrayList<>();
            Random rand = new Random();

            for (int i = 0; i < FIRST_NAMES.length; i++) {
                Author author = new Author();
                author.setFirstName(FIRST_NAMES[i]);
                author.setSecondName(LAST_NAMES[i % LAST_NAMES.length]);
                author.setCountry("Polska");
                author.setBirthYear("19" + (50 + i));
                authors.add(author);
            }
            authorRepository.saveAll(authors);

            for (int i = 0; i < 50; i++) {
                Book book = new Book();
                book.setTitle(BOOK_TITLES[i % BOOK_TITLES.length] + " Tom " + ((i / BOOK_TITLES.length) + 1));
                book.setPages(rand.nextInt(100) + 100);
                book.setRentFlag(rand.nextBoolean());
                book.setAuthor(authors.get(rand.nextInt(authors.size())));
                bookRepository.save(book);
            }
        };
    }
}
