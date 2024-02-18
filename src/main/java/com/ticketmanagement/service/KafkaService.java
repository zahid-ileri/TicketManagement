package com.ticketmanagement.service;

import com.ticketmanagement.model.kafka.TicketEvent;
import org.springframework.stereotype.Service;

@Service
public interface KafkaService {
    void sendMessage(TicketEvent ticketEvent);
}
