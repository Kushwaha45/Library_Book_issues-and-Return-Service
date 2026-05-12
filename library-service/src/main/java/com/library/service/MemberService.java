package com.library.service;

import com.library.dto.MemberDTO;
import com.library.exception.BusinessRuleException;
import com.library.exception.ResourceNotFoundException;
import com.library.model.IssueRecord;
import com.library.model.Member;
import com.library.repository.IssueRecordRepository;
import com.library.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final IssueRecordRepository issueRecordRepository;

    public MemberService(MemberRepository memberRepository, IssueRecordRepository issueRecordRepository) {
        this.memberRepository = memberRepository;
        this.issueRecordRepository = issueRecordRepository;
    }

    /**
     * Register a new member.
     */
    public Member registerMember(MemberDTO memberDTO) {
        // Check for duplicate email
        if (memberRepository.existsByEmail(memberDTO.getEmail())) {
            throw new BusinessRuleException("A member with email '" + memberDTO.getEmail() + "' already exists");
        }

        Member member = new Member();
        member.setName(memberDTO.getName());
        member.setEmail(memberDTO.getEmail());
        return memberRepository.save(member);
    }

    /**
     * Get all members.
     */
    @Transactional(readOnly = true)
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    /**
     * Get member details by ID.
     */
    @Transactional(readOnly = true)
    public Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member", "memberId", memberId));
    }

    /**
     * Get all books currently issued to a member (active issues only).
     */
    @Transactional(readOnly = true)
    public List<IssueRecord> getBooksIssuedToMember(Long memberId) {
        // Verify member exists
        if (!memberRepository.existsById(memberId)) {
            throw new ResourceNotFoundException("Member", "memberId", memberId);
        }
        return issueRecordRepository.findByMemberMemberIdAndReturnDateIsNull(memberId);
    }
}
