package com.ticketmanagement.model.entity;

import jakarta.persistence.*;

@Entity
public class Priority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private PriorityType priorityType;

    public Priority() {
    }

    public Priority(PriorityType priorityType) {
        this.priorityType = priorityType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PriorityType getPriorityType() {
        return priorityType;
    }

    public void setPriorityType(PriorityType priorityType) {
        this.priorityType = priorityType;
    }

    @Override
    public String toString() {
        return "Priority{" +
                "id=" + id +
                ", priorityType=" + priorityType +
                '}';
    }
}
