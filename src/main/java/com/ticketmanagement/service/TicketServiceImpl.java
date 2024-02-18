package com.ticketmanagement.service;

import com.ticketmanagement.model.entity.*;
import com.ticketmanagement.model.kafka.TicketEvent;
import com.ticketmanagement.model.response.TicketSummary;
import com.ticketmanagement.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService{

    private final TicketRepository ticketRepository;
    private final CategoryRepository categoryRepository;
    private final ContactRepository contactRepository;
    private final GroupRepository groupRepository;
    private final IssueRepository issueRepository;
    private final PriorityRepository priorityRepository;
    private final StatusRepository statusRepository;
    private final KafkaService kafkaService;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, CategoryRepository categoryRepository, ContactRepository contactRepository, GroupRepository groupRepository, IssueRepository issueRepository, PriorityRepository priorityRepository, StatusRepository statusRepository, KafkaService kafkaService) {
        this.ticketRepository = ticketRepository;
        this.categoryRepository = categoryRepository;
        this.contactRepository = contactRepository;
        this.groupRepository = groupRepository;
        this.issueRepository = issueRepository;
        this.priorityRepository = priorityRepository;
        this.statusRepository = statusRepository;
        this.kafkaService = kafkaService;
    }


    @Override
    public ResponseEntity<Page<TicketSummary>> getAllTickets(Optional<StatusType> status, Optional<PriorityType> priority,
                                                             Optional<String> contact, Optional<IssueType> issueType,
                                                             Optional<String> group, Optional<String> category,
                                                             Optional<String> sortBy, Optional<String> sortType,
                                                             Optional<Integer> page, Optional<Integer> offset) {
        Pageable pageable = createPageRequest(page, offset, sortType, sortBy);
        return ResponseEntity.ok(ticketRepository.getTickets(status, priority, contact, issueType, group, category, pageable));
    }

    private Sort createSort(Optional<String> sortType, Optional<String> sortBy) {
        Sort.Direction direction = sortType.orElse("asc").equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        return Sort.by(direction, sortBy.orElse("lastModifiedDate"));
    }

    private Pageable createPageRequest(Optional<Integer> page, Optional<Integer> offset, Optional<String> sortType, Optional<String> sortBy) {
        Sort sort = createSort(sortType, sortBy);
        return PageRequest.of(page.orElse(0), offset.orElse(20), sort);
    }

    @Override
    public ResponseEntity<Ticket> newTicket(Ticket ticket) {
        return ResponseEntity.ok(saveTicket(ticket));
    }

    private Ticket saveTicket(Ticket ticket) {
        ticket.setCategory(findOrSaveCategoryByName(ticket));
        ticket.setContact(findOrSaveContactByName(ticket));
        ticket.setGroup(findOrSaveGroupByName(ticket));
        ticket.setIssueType(findOrSaveIssueByIssueType(ticket));
        ticket.setPriority(findOrSavePriorityByPriorityType(ticket));
        ticket.setStatus(findOrSaveStatusByStatusType(ticket));
        return ticketRepository.save(ticket);
    }

    private Category findOrSaveCategoryByName(Ticket ticket) {
        String categoryName = ticket.getCategory() != null ? ticket.getCategory().getCategoryName() : null;
        return categoryName == null ? null : categoryRepository.findCategoryByCategoryName(categoryName).orElseGet(() -> categoryRepository.save(new Category(categoryName)));
    }

    private Contact findOrSaveContactByName(Ticket ticket) {
        String contactName = ticket.getContact() != null ? ticket.getContact().getContactName() : null;
        return contactName == null ? null : contactRepository.findContactByContactName(contactName).orElseGet(() -> contactRepository.save(new Contact(contactName)));
    }

    private Group findOrSaveGroupByName(Ticket ticket) {
        String groupName = ticket.getGroup() != null ? ticket.getGroup().getGroupName() : null;
        return groupName == null ? null : groupRepository.findTicketGroupByGroupName(groupName).orElseGet(() -> groupRepository.save(new Group(groupName)));
    }

    private Issue findOrSaveIssueByIssueType(Ticket ticket) {
        IssueType issueType = ticket.getIssueType() != null ? ticket.getIssueType().getIssueType() : null;
        return issueType == null ? null : issueRepository.findIssueByIssueType(issueType).orElseGet(() -> issueRepository.save(new Issue(issueType)));
    }

    private Priority findOrSavePriorityByPriorityType(Ticket ticket) {
        PriorityType priorityType = ticket.getPriority() != null ? ticket.getPriority().getPriorityType() : null;
        return priorityType == null ? null : priorityRepository.findPriorityByPriorityType(priorityType).orElseGet(() -> priorityRepository.save(new Priority(priorityType)));
    }

    private Status findOrSaveStatusByStatusType(Ticket ticket) {
        StatusType statusType = ticket.getStatus() != null ? ticket.getStatus().getStatusType() : null;
        return statusType == null ? null : statusRepository.findStatusByStatusType(statusType).orElseGet(() -> statusRepository.save(new Status(statusType)));
    }

    @Override
    public ResponseEntity<Ticket> updateTicket(Ticket ticket) {
        Optional<Ticket> existingTicketOpt = ticketRepository.findById(ticket.getId());

        return existingTicketOpt.map(existingTicket -> {
            if (isTicketStatusChanged(ticket, existingTicket)) {
                kafkaService.sendMessage(new TicketEvent().create(ticket));
            }
            updateExistingTicketFromProvided(ticket, existingTicket);
            return ResponseEntity.ok(saveTicket(existingTicket));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    private boolean isTicketStatusChanged(Ticket updatedTicket, Ticket existingTicket) {
        return !existingTicket.getStatus().equals(updatedTicket.getStatus());
    }

    private void updateExistingTicketFromProvided(Ticket provided, Ticket existing) {
        existing.setSummary(provided.getSummary());
        existing.setDescription(provided.getDescription());
        existing.setStatus(provided.getStatus());
        existing.setPriority(provided.getPriority());
        existing.setContact(provided.getContact());
        existing.setIssueType(provided.getIssueType());
        existing.setGroup(provided.getGroup());
        existing.setCategory(provided.getCategory());
    }

    @Override
    public ResponseEntity<?> deleteTicket(long ticketId) {
        boolean ticketExists = ticketRepository.existsById(ticketId);
        if (ticketExists) {
            ticketRepository.deleteById(ticketId);
        }
        return ticketExists ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Ticket> getTicketById(long ticketId) {
        Optional<Ticket> ticketOpt = ticketRepository.findById(ticketId);
        return ticketOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
