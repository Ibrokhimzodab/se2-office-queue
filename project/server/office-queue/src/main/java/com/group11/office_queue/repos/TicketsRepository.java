package com.group11.office_queue.repos;

import com.group11.office_queue.entities.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TicketsRepository extends JpaRepository<TicketEntity, Long> {

    Integer countAllByDateTimeAfterAndServiceId(LocalDateTime dateTime, Long serviceId);
    Integer countAllByServiceIdAndIsServedFalseAndDateTimeAfter(Long serviceId, LocalDateTime dateTime);
}
