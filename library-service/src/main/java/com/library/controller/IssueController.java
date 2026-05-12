package com.library.controller;

import com.library.dto.ApiResponse;
import com.library.dto.IssueRequestDTO;
import com.library.model.IssueRecord;
import com.library.service.IssueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
@Tag(name = "Issue & Return Management", description = "APIs for issuing and returning library books")
@CrossOrigin(origins = "*")
public class IssueController {

    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @PostMapping("/issue")
    @Operation(summary = "Issue a book", description = "Issue a book to a member. Book must be available and member must have less than 3 active issues.")
    public ResponseEntity<ApiResponse<IssueRecord>> issueBook(@Valid @RequestBody IssueRequestDTO request) {
        IssueRecord issueRecord = issueService.issueBook(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Book issued successfully", issueRecord));
    }

    @PutMapping("/return/{issueId}")
    @Operation(summary = "Return a book", description = "Return an issued book. Updates the return date and marks the book as available.")
    public ResponseEntity<ApiResponse<IssueRecord>> returnBook(@PathVariable Long issueId) {
        IssueRecord issueRecord = issueService.returnBook(issueId);
        return ResponseEntity.ok(ApiResponse.success("Book returned successfully", issueRecord));
    }

    @GetMapping
    @Operation(summary = "Get all issue records", description = "Retrieve all issue records (both active and returned)")
    public ResponseEntity<ApiResponse<List<IssueRecord>>> getAllIssueRecords() {
        List<IssueRecord> records = issueService.getAllIssueRecords();
        return ResponseEntity.ok(ApiResponse.success("Issue records retrieved successfully", records));
    }

    @GetMapping("/active")
    @Operation(summary = "Get active issues", description = "Retrieve all currently active (unreturned) issue records")
    public ResponseEntity<ApiResponse<List<IssueRecord>>> getActiveIssueRecords() {
        List<IssueRecord> records = issueService.getActiveIssueRecords();
        return ResponseEntity.ok(ApiResponse.success("Active issue records retrieved successfully", records));
    }
}
