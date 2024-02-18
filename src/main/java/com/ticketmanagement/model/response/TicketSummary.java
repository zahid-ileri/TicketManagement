package com.ticketmanagement.model.response;

public interface TicketSummary {
    long getId();
    String getSummary();
    String getStatus();
    String getPriority();
    String getIssueType();

}
