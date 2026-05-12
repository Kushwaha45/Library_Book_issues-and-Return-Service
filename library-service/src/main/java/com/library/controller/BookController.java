package com.library.controller;

import com.library.dto.ApiResponse;
import com.library.dto.BookDTO;
import com.library.model.Book;
import com.library.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Book Management", description = "APIs for managing library books")
@CrossOrigin(origins = "*")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    @Operation(summary = "Add a new book", description = "Add a new book to the library catalog")
    public ResponseEntity<ApiResponse<Book>> addBook(@Valid @RequestBody BookDTO bookDTO) {
        Book book = bookService.addBook(bookDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Book added successfully", book));
    }

    @GetMapping
    @Operation(summary = "Get all books", description = "Retrieve a list of all books in the library")
    public ResponseEntity<ApiResponse<List<Book>>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(ApiResponse.success("Books retrieved successfully", books));
    }

    @GetMapping("/{bookId}")
    @Operation(summary = "Get book by ID", description = "Retrieve a specific book by its ID")
    public ResponseEntity<ApiResponse<Book>> getBookById(@PathVariable Long bookId) {
        Book book = bookService.getBookById(bookId);
        return ResponseEntity.ok(ApiResponse.success("Book retrieved successfully", book));
    }

    @GetMapping("/available")
    @Operation(summary = "Get available books", description = "Retrieve all books that are currently available for issuing")
    public ResponseEntity<ApiResponse<List<Book>>> getAvailableBooks() {
        List<Book> books = bookService.getAvailableBooks();
        return ResponseEntity.ok(ApiResponse.success("Available books retrieved successfully", books));
    }

    @GetMapping("/search")
    @Operation(summary = "Search books", description = "Search books by title and/or author (partial, case-insensitive)")
    public ResponseEntity<ApiResponse<List<Book>>> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author) {
        List<Book> books = bookService.searchBooks(title, author);
        return ResponseEntity.ok(ApiResponse.success("Search results retrieved successfully", books));
    }
}
