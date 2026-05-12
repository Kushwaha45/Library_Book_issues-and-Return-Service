package com.library.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "issue_records")
public class IssueRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long issueId;

    @Column(nullable = false)
    private LocalDate issueDate;

    private LocalDate returnDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id", nullable = false)
    @JsonIgnoreProperties({"issueRecords"})
    private Book book;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id", nullable = false)
    @JsonIgnoreProperties({"issueRecords"})
    private Member member;

    public IssueRecord() {}

    public IssueRecord(Long issueId, LocalDate issueDate, LocalDate returnDate, Book book, Member member) {
        this.issueId = issueId;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
        this.book = book;
        this.member = member;
    }

    public Long getIssueId() { return issueId; }
    public void setIssueId(Long issueId) { this.issueId = issueId; }

    public LocalDate getIssueDate() { return issueDate; }
    public void setIssueDate(LocalDate issueDate) { this.issueDate = issueDate; }

    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }

    public Member getMember() { return member; }
    public void setMember(Member member) { this.member = member; }
}
