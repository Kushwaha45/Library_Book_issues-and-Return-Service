package com.library.controller;

import com.library.dto.ApiResponse;
import com.library.dto.MemberDTO;
import com.library.model.IssueRecord;
import com.library.model.Member;
import com.library.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@Tag(name = "Member Management", description = "APIs for managing library members")
@CrossOrigin(origins = "*")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    @Operation(summary = "Register a member", description = "Register a new library member")
    public ResponseEntity<ApiResponse<Member>> registerMember(@Valid @RequestBody MemberDTO memberDTO) {
        Member member = memberService.registerMember(memberDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Member registered successfully", member));
    }

    @GetMapping
    @Operation(summary = "Get all members", description = "Retrieve a list of all registered members")
    public ResponseEntity<ApiResponse<List<Member>>> getAllMembers() {
        List<Member> members = memberService.getAllMembers();
        return ResponseEntity.ok(ApiResponse.success("Members retrieved successfully", members));
    }

    @GetMapping("/{memberId}")
    @Operation(summary = "Get member details", description = "Retrieve details of a specific member by ID")
    public ResponseEntity<ApiResponse<Member>> getMemberById(@PathVariable Long memberId) {
        Member member = memberService.getMemberById(memberId);
        return ResponseEntity.ok(ApiResponse.success("Member retrieved successfully", member));
    }

    @GetMapping("/{memberId}/books")
    @Operation(summary = "Get books issued to member", description = "Retrieve all books currently issued to a specific member")
    public ResponseEntity<ApiResponse<List<IssueRecord>>> getBooksIssuedToMember(@PathVariable Long memberId) {
        List<IssueRecord> issueRecords = memberService.getBooksIssuedToMember(memberId);
        return ResponseEntity.ok(ApiResponse.success("Issued books retrieved successfully", issueRecords));
    }
}
