package com.library.service;

import com.library.dto.IssueRequestDTO;
import com.library.exception.BusinessRuleException;
import com.library.exception.ResourceNotFoundException;
import com.library.model.Book;
import com.library.model.IssueRecord;
import com.library.model.Member;
import com.library.repository.BookRepository;
import com.library.repository.IssueRecordRepository;
import com.library.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class IssueService {

    private static final int MAX_ACTIVE_ISSUES = 3;

    private final IssueRecordRepository issueRecordRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    public IssueService(IssueRecordRepository issueRecordRepository,
                        BookRepository bookRepository,
                        MemberRepository memberRepository) {
        this.issueRecordRepository = issueRecordRepository;
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
    }

    /**
     * Issue a book to a member.
     *
     * Business Rules:
     * 1. Book must exist and be available
     * 2. Member must exist
     * 3. Member can have a maximum of 3 active book issues
     * 4. Same book cannot be issued to same member if already active
     */
    public IssueRecord issueBook(IssueRequestDTO request) {
        // 1. Validate book exists
        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book", "bookId", request.getBookId()));

        // 2. Check book availability
        if (!book.getAvailable()) {
            throw new BusinessRuleException("Book '" + book.getTitle() + "' is not available for issuing. It is currently issued to another member.");
        }

        // 3. Validate member exists
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member", "memberId", request.getMemberId()));

        // 4. Check max active issues (maximum 3)
        long activeIssueCount = issueRecordRepository.countByMemberMemberIdAndReturnDateIsNull(member.getMemberId());
        if (activeIssueCount >= MAX_ACTIVE_ISSUES) {
            throw new BusinessRuleException("Member '" + member.getName() + "' has already reached the maximum limit of " + MAX_ACTIVE_ISSUES + " active book issues. Please return a book before issuing a new one.");
        }

        // 5. Check if this book is already issued to this member
        boolean alreadyIssued = issueRecordRepository.existsByBookBookIdAndMemberMemberIdAndReturnDateIsNull(
                book.getBookId(), member.getMemberId());
        if (alreadyIssued) {
            throw new BusinessRuleException("Book '" + book.getTitle() + "' is already issued to member '" + member.getName() + "'.");
        }

        // 6. Create issue record
        IssueRecord issueRecord = new IssueRecord();
        issueRecord.setBook(book);
        issueRecord.setMember(member);
        issueRecord.setIssueDate(LocalDate.now());
        issueRecord.setReturnDate(null);

        // 7. Mark book as unavailable
        book.setAvailable(false);
        bookRepository.save(book);

        return issueRecordRepository.save(issueRecord);
    }

    /**
     * Return an issued book.
     *
     * Updates return date and marks book as available.
     */
    public IssueRecord returnBook(Long issueId) {
        // 1. Find the issue record
        IssueRecord issueRecord = issueRecordRepository.findById(issueId)
                .orElseThrow(() -> new ResourceNotFoundException("IssueRecord", "issueId", issueId));

        // 2. Check if already returned
        if (issueRecord.getReturnDate() != null) {
            throw new BusinessRuleException("This book has already been returned on " + issueRecord.getReturnDate());
        }

        // 3. Update return date
        issueRecord.setReturnDate(LocalDate.now());

        // 4. Mark book as available
        Book book = issueRecord.getBook();
        book.setAvailable(true);
        bookRepository.save(book);

        return issueRecordRepository.save(issueRecord);
    }

    /**
     * Get all issue records.
     */
    @Transactional(readOnly = true)
    public List<IssueRecord> getAllIssueRecords() {
        return issueRecordRepository.findAll();
    }

    /**
     * Get all active (unreturned) issue records.
     */
    @Transactional(readOnly = true)
    public List<IssueRecord> getActiveIssueRecords() {
        return issueRecordRepository.findByReturnDateIsNull();
    }
}
