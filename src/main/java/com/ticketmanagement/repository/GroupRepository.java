package com.ticketmanagement.repository;

import com.ticketmanagement.model.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findTicketGroupByGroupName(String groupName);
}
