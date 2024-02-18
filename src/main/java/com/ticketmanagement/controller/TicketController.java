package com.ticketmanagement.controller;

import com.ticketmanagement.model.entity.IssueType;
import com.ticketmanagement.model.entity.PriorityType;
import com.ticketmanagement.model.entity.StatusType;
import com.ticketmanagement.model.entity.Ticket;
import com.ticketmanagement.model.response.TicketSummary;
import com.ticketmanagement.service.TicketService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {

    private final TicketService ticketService;
    private final Logger logger = LogManager.getLogger(TicketController.class);

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public ResponseEntity<Page<TicketSummary>> getAllTickets(Optional<StatusType> status, Optional<PriorityType> priority,
                                                             Optional<String> contact, Optional<IssueType> issueType,
                                                             Optional<String> group, Optional<String> category,
                                                             Optional<String> sortBy, Optional<String> sortType,
                                                             Optional<Integer> page, Optional<Integer> offset) {
        logger.info("New getAllTickets request received");
        return ticketService.getAllTickets(status, priority, contact, issueType, group, category, sortBy, sortType,
                page, offset);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicket(@PathVariable(name = "id") long id) {
        return ticketService.getTicketById(id);
    }

    @PostMapping("/new-ticket")
    public ResponseEntity<Ticket> newTicket(@RequestBody Ticket ticket) {
        logger.info("New newTicket request received: {}", ticket);
        return ticketService.newTicket(ticket);
    }

    @PutMapping("/update-ticket")
    public ResponseEntity<Ticket> updateTicket(@RequestBody Ticket ticket) {
        logger.info("New updateTicket request received: {}", ticket);
        return ticketService.updateTicket(ticket);
    }

    @DeleteMapping("/delete-ticket/{id}")
    public ResponseEntity<?> deleteTicket(@PathVariable(name = "id") long ticketId) {
        logger.info("New deleteTicket request received: {}", ticketId);
        return ticketService.deleteTicket(ticketId);
    }
}
