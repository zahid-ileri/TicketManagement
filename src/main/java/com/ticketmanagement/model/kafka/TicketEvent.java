package com.ticketmanagement.model.kafka;

import com.ticketmanagement.model.entity.StatusType;
import com.ticketmanagement.model.entity.Ticket;

import java.time.LocalDateTime;

public class TicketEvent {
    private long ticketId;
    private String summary;
    private StatusType status;
    private LocalDateTime lastModifiedDate;

    public TicketEvent() {
    }

    public TicketEvent(long ticketId, String summary, StatusType status, LocalDateTime lastModifiedDate) {
        this.ticketId = ticketId;
        this.summary = summary;
        this.status = status;
        this.lastModifiedDate = lastModifiedDate;
    }

    public long getTicketId() {
        return ticketId;
    }

    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public TicketEvent create(Ticket ticket) {
        TicketEvent ticketEvent = new TicketEvent();
        ticketEvent.setTicketId(ticket.getId());
        ticketEvent.setSummary(ticket.getSummary());
        ticketEvent.setStatus(ticket.getStatus().getStatusType());
        ticketEvent.setLastModifiedDate(LocalDateTime.now());
        return ticketEvent;
    }

    @Override
    public String toString() {
        return "TicketEvent{" +
                "ticketId=" + ticketId +
                ", summary='" + summary + '\'' +
                ", status=" + status +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}
