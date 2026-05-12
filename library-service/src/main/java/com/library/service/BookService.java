package com.library.service;

import com.library.dto.BookDTO;
import com.library.exception.ResourceNotFoundException;
import com.library.model.Book;
import com.library.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Add a new book to the library.
     */
    public Book addBook(BookDTO bookDTO) {
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setAvailable(true);
        return bookRepository.save(book);
    }

    /**
     * Get all books in the library.
     */
    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    /**
     * Get a book by its ID.
     */
    @Transactional(readOnly = true)
    public Book getBookById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "bookId", bookId));
    }

    /**
     * Get all available books.
     */
    @Transactional(readOnly = true)
    public List<Book> getAvailableBooks() {
        return bookRepository.findByAvailableTrue();
    }

    /**
     * Search books by title and/or author.
     */
    @Transactional(readOnly = true)
    public List<Book> searchBooks(String title, String author) {
        return bookRepository.searchBooks(title, author);
    }
}
