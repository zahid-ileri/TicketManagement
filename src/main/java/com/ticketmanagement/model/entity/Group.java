package com.ticketmanagement.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ticket_group")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String groupName;

    public Group() {
    }

    public Group(String groupName) {
        this.groupName = groupName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return "TicketGroup{" +
                "id=" + id +
                ", groupName='" + groupName + '\'' +
                '}';
    }
}
