package com.ticketmanagement.repository;

import com.ticketmanagement.model.entity.Priority;
import com.ticketmanagement.model.entity.PriorityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PriorityRepository extends JpaRepository<Priority, Long> {
    Optional<Priority> findPriorityByPriorityType(PriorityType priorityType);
}
