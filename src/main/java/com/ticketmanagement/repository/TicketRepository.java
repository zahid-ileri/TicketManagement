package com.ticketmanagement.repository;

import com.ticketmanagement.model.entity.IssueType;
import com.ticketmanagement.model.entity.PriorityType;
import com.ticketmanagement.model.entity.StatusType;
import com.ticketmanagement.model.entity.Ticket;
import com.ticketmanagement.model.response.TicketSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query("select t.id as id, t.summary as summary, t.status.statusType as status, t.priority.priorityType as priority, " +
            "t.issueType.issueType as issueType from Ticket t where :status is null or t.status.statusType=:status and " +
            ":priority is null or t.priority.priorityType=:priority and :contact is null or t.priority.priorityType=:contact " +
            "and :issueType is null or t.issueType.issueType=:issueType and :group is null or t.group.groupName=:group " +
            "and :category is null or t.category.categoryName=:category")
    Page<TicketSummary> getTickets(@Param("status") Optional<StatusType> status, @Param("priority") Optional<PriorityType> priority,
                                   @Param("contact") Optional<String> contact, @Param("issueType") Optional<IssueType> issueType,
                                   @Param("group") Optional<String> group, @Param("category") Optional<String> category,
                                   Pageable pageable);
}
