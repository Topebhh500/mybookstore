package com.example.bookstore.web;

import com.example.bookstore.domain.Book;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class BookController {
    
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping({"/", "/booklist"})
    public String showBookList(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "booklist";
    }

    @GetMapping("/addbook")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("categories", categoryRepository.findAll());
        return "addbook";
    }

    @PostMapping("/addbook")
    public String addBook(@ModelAttribute Book book, RedirectAttributes redirectAttributes) {
        try {
            bookRepository.save(book);
            redirectAttributes.addFlashAttribute("message", "Book added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error adding book: " + e.getMessage());
        }
        return "redirect:/booklist";
    }

    @GetMapping("/editbook/{id}")
    public String showEditBookForm(@PathVariable("id") Long id, Model model) {
        Book book = bookRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        model.addAttribute("book", book);
        model.addAttribute("categories", categoryRepository.findAll());
        return "editbook";
    }

    @PostMapping("/editbook/{id}")
    public String updateBook(@PathVariable("id") Long id, @ModelAttribute Book book, 
                           RedirectAttributes redirectAttributes) {
        try {
            book.setId(id);
            bookRepository.save(book);
            redirectAttributes.addFlashAttribute("message", "Book updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating book: " + e.getMessage());
        }
        return "redirect:/booklist";
    }

    @GetMapping("/deletebook/{id}")
    public String deleteBook(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            bookRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Book deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting book: " + e.getMessage());
        }
        return "redirect:/booklist";
    }
}