package com.group11.office_queue.repos;

import com.group11.office_queue.entities.CounterEntity;
import com.group11.office_queue.entities.TicketEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TicketsRepository extends JpaRepository<TicketEntity, Long> {

    Integer countAllByDateTimeAfterAndServiceId(LocalDateTime dateTime, Long serviceId);
    Integer countAllByServiceIdAndIsServedFalseAndDateTimeAfter(Long serviceId, LocalDateTime dateTime);
    List<TicketEntity> findAllByIsServedFalseAndDateTimeAfter(LocalDateTime dateTime);
    Optional<TicketEntity> findFirstByCounterIdAndIsServedFalse(Long counterId);
    List<TicketEntity> findAllByServiceIdInAndIsServedFalseAndDateTimeAfterAndCounter(List<Long> serviceIds, LocalDateTime dateTime, CounterEntity counter, Sort sort);
}
