package com.ticketmanagement.repository;

import com.ticketmanagement.model.entity.Status;
import com.ticketmanagement.model.entity.StatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
    Optional<Status> findStatusByStatusType(StatusType statusType);
}
