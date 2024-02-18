package com.ticketmanagement.service;

import com.ticketmanagement.model.kafka.TicketEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaServiceImpl implements KafkaService{
    private final KafkaTemplate<String, TicketEvent> kafkaTemplate;
    @Value("${ticket.kafka.topic}")
    private String kafkaTopic;

    @Autowired
    public KafkaServiceImpl(KafkaTemplate<String, TicketEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    @Override
    public void sendMessage(TicketEvent ticketEvent) {
        kafkaTemplate.send(kafkaTopic, "ticketEvent", ticketEvent);
    }
}
