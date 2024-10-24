package com.example.bookstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.bookstore.domain.Book;
import com.example.bookstore.domain.Category;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.CategoryRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookstoreApplication {

    private static final Logger log = LoggerFactory.getLogger(BookstoreApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(BookstoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(BookRepository bookRepository, CategoryRepository categoryRepository) {
        return (args) -> {
            // Create and save categories
            if (categoryRepository.count() == 0) {
                Category fiction = new Category("Fiction");
                Category programming = new Category("Programming");
                Category nonFiction = new Category("Non-Fiction");

                categoryRepository.save(fiction);
                categoryRepository.save(programming);
                categoryRepository.save(nonFiction);

                // Create and save books
                bookRepository.save(new Book("Effective Java", "Joshua Bloch", 2008, "978-0134685991", 45.00, programming));
                bookRepository.save(new Book("Clean Code", "Robert C. Martin", 2008, "978-0132350884", 40.00, programming));
                bookRepository.save(new Book("Java Concurrency in Practice", "Brian Goetz", 2006, "978-0321349606", 50.00, programming));
                bookRepository.save(new Book("Sapiens: A Brief History of Humankind", "Yuval Noah Harari", 2011, "978-0099590088", 35.00, nonFiction));

                log.info("fetch all books");
                for (Book book : bookRepository.findAll()) {
                    log.info(book.toString());
                }
                
                log.info("fetch all categories");
                for (Category category : categoryRepository.findAll()) {
                    log.info(category.toString());
                }    
            }
        };
    }
}
