package com.ticketmanagement.service;

import com.ticketmanagement.model.entity.IssueType;
import com.ticketmanagement.model.entity.PriorityType;
import com.ticketmanagement.model.entity.StatusType;
import com.ticketmanagement.model.entity.Ticket;
import com.ticketmanagement.model.response.TicketSummary;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface TicketService {
    ResponseEntity<Page<TicketSummary>> getAllTickets(Optional<StatusType> status, Optional<PriorityType> priority,
                                                      Optional<String> contact, Optional<IssueType> issueType,
                                                      Optional<String> ticketGroup, Optional<String> category,
                                                      Optional<String> sortBy, Optional<String> sortType,
                                                      Optional<Integer> page, Optional<Integer> offset);
    ResponseEntity<Ticket> newTicket(Ticket ticket);
    ResponseEntity<Ticket> updateTicket(Ticket ticket);
    ResponseEntity<?> deleteTicket(long ticketId);
    ResponseEntity<Ticket> getTicketById(long ticketId);
}
