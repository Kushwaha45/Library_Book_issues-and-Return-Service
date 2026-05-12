package com.library.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private Boolean available = true;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"book"})
    private List<IssueRecord> issueRecords = new ArrayList<>();

    public Book() {}

    public Book(Long bookId, String title, String author, Boolean available) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.available = available;
    }

    public Long getBookId() { return bookId; }
    public void setBookId(Long bookId) { this.bookId = bookId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public Boolean getAvailable() { return available; }
    public void setAvailable(Boolean available) { this.available = available; }

    public List<IssueRecord> getIssueRecords() { return issueRecords; }
    public void setIssueRecords(List<IssueRecord> issueRecords) { this.issueRecords = issueRecords; }
}
