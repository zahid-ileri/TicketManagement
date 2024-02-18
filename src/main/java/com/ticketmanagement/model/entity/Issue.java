package com.ticketmanagement.model.entity;

import jakarta.persistence.*;

@Entity
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private IssueType issueType;

    public Issue() {
    }

    public Issue(IssueType issueType) {
        this.issueType = issueType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public IssueType getIssueType() {
        return issueType;
    }

    public void setIssueType(IssueType issueType) {
        this.issueType = issueType;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "id=" + id +
                ", issueType=" + issueType +
                '}';
    }
}
