package com.library.repository;

import com.library.model.IssueRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRecordRepository extends JpaRepository<IssueRecord, Long> {

    List<IssueRecord> findByMemberMemberIdAndReturnDateIsNull(Long memberId);

    List<IssueRecord> findByReturnDateIsNull();

    List<IssueRecord> findByBookBookIdAndReturnDateIsNull(Long bookId);

    long countByMemberMemberIdAndReturnDateIsNull(Long memberId);

    boolean existsByBookBookIdAndMemberMemberIdAndReturnDateIsNull(Long bookId, Long memberId);
}
