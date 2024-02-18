package com.ticketmanagement.model.entity;

import jakarta.persistence.*;

@Entity
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private StatusType statusType;

    public Status() {
    }

    public Status(StatusType statusType) {
        this.statusType = statusType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public StatusType getStatusType() {
        return statusType;
    }

    public void setStatusType(StatusType statusType) {
        this.statusType = statusType;
    }

    @Override
    public String toString() {
        return "Status{" +
                "id=" + id +
                ", statusType=" + statusType +
                '}';
    }
}
