package com.ticketmanagement.repository;

import com.ticketmanagement.model.entity.Issue;
import com.ticketmanagement.model.entity.IssueType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
    Optional<Issue> findIssueByIssueType(IssueType issueType);
}
