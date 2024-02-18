package com.ticketmanagement.model.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(
        indexes = {
                @Index(columnList = "status_id"),
                @Index(columnList = "priority_id"),
                @Index(columnList = "contact_id"),
                @Index(columnList = "issue_type_id"),
                @Index(columnList = "group_id"),
                @Index(columnList = "category_id"),
                @Index(columnList = "id, summary, status_id, priority_id, issue_type_id")

        }
)
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String summary;
    private String description;
    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
    @ManyToOne(fetch = FetchType.EAGER)
    private Status status;
    @ManyToOne(fetch = FetchType.EAGER)
    private Priority priority;
    @ManyToOne(fetch = FetchType.EAGER)
    private Contact contact;
    @ManyToOne(fetch = FetchType.EAGER)
    private Issue issueType;
    @ManyToOne(fetch = FetchType.EAGER)
    private Group group;
    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;

    public Ticket() {
    }

    public Ticket(String summary, String description, Status status, Priority priority, Contact contact, Issue issueType, Group group, Category category) {
        this.summary = summary;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.contact = contact;
        this.issueType = issueType;
        this.group = group;
        this.category = category;
    }

    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
        lastModifiedDate = LocalDateTime.now();
    }
    @PreUpdate
    public void preUpdate() {
        lastModifiedDate = LocalDateTime.now();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Issue getIssueType() {
        return issueType;
    }

    public void setIssueType(Issue issueType) {
        this.issueType = issueType;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", summary='" + summary + '\'' +
                ", description='" + description + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                ", status=" + status +
                ", priority=" + priority +
                ", contact=" + contact +
                ", issueType=" + issueType +
                ", group=" + group +
                ", category=" + category +
                '}';
    }
}
