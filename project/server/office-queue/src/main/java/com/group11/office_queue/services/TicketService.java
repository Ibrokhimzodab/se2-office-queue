package com.group11.office_queue.services;

import com.group11.office_queue.entities.CounterEntity;
import com.group11.office_queue.entities.ServiceEntity;
import com.group11.office_queue.entities.TicketEntity;
import com.group11.office_queue.models.ServiceDTO;
import com.group11.office_queue.models.TicketDTO;
import com.group11.office_queue.repos.CountersRepository;
import com.group11.office_queue.repos.ServicesRepository;
import com.group11.office_queue.repos.TicketsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class TicketService {

    private final TicketsRepository ticketRepository;
    private final ServicesRepository serviceRepository;
    private final CountersRepository counterRepository;

    public TicketDTO getNewTicket(Long serviceId) {
        var service = serviceRepository.findById(serviceId).orElseThrow(() -> new RuntimeException("Service not found"));
        var todayStartingTime = LocalDateTime.now().withHour(6).withMinute(0).withSecond(0).withNano(0);
        var ticketsCount = ticketRepository
                .countAllByDateTimeAfterAndServiceId(todayStartingTime, serviceId);
        var ticket = new TicketEntity();
        ticket.setService(service);
        ticket.setDateTime(LocalDateTime.now());
        ticket.setIsServed(false);
        ticket.setWaitListCode(String.valueOf(service.getName().charAt(0)) + ticketsCount + 1);
        ticket = ticketRepository.save(ticket);
        var result = new TicketDTO();
        result.setId(ticket.getId());
        result.setWaitListCode(ticket.getWaitListCode());
        result.setDateTime(ticket.getDateTime());
        result.setEstimatedTime(calculateEstimatedWaitingTime(service, todayStartingTime));
        return result;
    }

    private LocalTime calculateEstimatedWaitingTime(ServiceEntity serviceEntity, LocalDateTime todayStartingTime) {
        double tr = serviceEntity.getDurationInMinutes(); // Service time for request type r
        long nr = ticketRepository.countAllByServiceIdAndIsServedFalseAndDateTimeAfter(serviceEntity.getId(), todayStartingTime); // Number of people in queue for request type r
        List<CounterEntity> counterEntities = counterRepository.findAllByServicesIn(List.of(Set.of(serviceEntity)));

        double sum = 0;
        for (CounterEntity counterEntity : counterEntities) {
            int ki = counterEntity.getServices().size(); // Number of different types of requests served by counter i
            sum += (1.0 / ki);
        }
        double Tr = tr * ((nr / sum) + 0.5);

        long minutes = Math.round(Tr);
        return LocalTime.of((int) (minutes / 60), (int) (minutes % 60));
    }

    public List<ServiceDTO> getServices() {
        return serviceRepository.findAll().stream().map(serviceEntity -> {
            var serviceDTO = new ServiceDTO();
            serviceDTO.setId(serviceEntity.getId());
            serviceDTO.setName(serviceEntity.getName());
            serviceDTO.setDurationInMinutes(serviceEntity.getDurationInMinutes());
            return serviceDTO;
        }).toList();
    }
}
