package com.group11.office_queue.services;

import com.group11.office_queue.entities.CounterEntity;
import com.group11.office_queue.entities.ServiceEntity;
import com.group11.office_queue.entities.TicketEntity;
import com.group11.office_queue.models.NextCustomerDTO;
import com.group11.office_queue.models.QueueDTO;
import com.group11.office_queue.models.ServiceDTO;
import com.group11.office_queue.models.TicketDTO;
import com.group11.office_queue.repos.CountersRepository;
import com.group11.office_queue.repos.ServicesRepository;
import com.group11.office_queue.repos.TicketsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TicketService {

    private final TicketsRepository ticketRepository;
    private final ServicesRepository serviceRepository;
    private final CountersRepository counterRepository;

    public TicketDTO getNewTicket(Long serviceId) {
        var service = serviceRepository.findById(serviceId).orElseThrow(() -> new RuntimeException("Service not found"));
        var todayStartingTime = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        var ticketsCount = ticketRepository
                .countAllByDateTimeAfterAndServiceId(todayStartingTime, serviceId);
        var ticket = new TicketEntity();
        ticket.setService(service);
        ticket.setDateTime(LocalDateTime.now());
        ticket.setIsServed(false);
        ticket.setWaitListCode(String.valueOf(service.getName().charAt(0)) + (ticketsCount + 1));
        ticket = ticketRepository.save(ticket);
        var result = new TicketDTO();
        result.setId(ticket.getId());
        result.setWaitListCode(ticket.getWaitListCode());
        result.setServiceId(ticket.getService().getId());
        result.setServiceName(ticket.getService().getName());
        result.setDateTime(ticket.getDateTime());
        result.setEstimatedTime(calculateEstimatedWaitingTime(service, todayStartingTime));
        return result;
    }

    private LocalTime calculateEstimatedWaitingTime(ServiceEntity serviceEntity, LocalDateTime todayStartingTime) {
        double tr = serviceEntity.getDurationInMinutes(); // Service time for request type r
        long nr = ticketRepository.countAllByServiceIdAndIsServedFalseAndDateTimeAfter(serviceEntity.getId(), todayStartingTime); // Number of people in queue for request type r
        List<CounterEntity> counterEntities = counterRepository.findAllByServicesContaining(serviceEntity);

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

    public List<QueueDTO> getAllTicketsInQueues() {
        var todayStartingTime = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        var tickets = ticketRepository.findAllByIsServedFalseAndDateTimeAfter(todayStartingTime);

        Map<ServiceEntity, List<TicketEntity>> ticketMap = tickets.stream()
                .sorted(Comparator.comparing(TicketEntity::getDateTime))
                .collect(Collectors.groupingBy(
                        TicketEntity::getService,
                        LinkedHashMap::new,
                        Collectors.toList()
                ));

        return ticketMap.entrySet().stream().map(entry -> {
            var queueDTO = new QueueDTO();
            queueDTO.setServiceId(entry.getKey().getId());
            queueDTO.setServiceName(entry.getKey().getName());
            queueDTO.setTickets(entry.getValue().stream()
                    .map(ticket -> new TicketDTO(
                            ticket.getId(),
                            ticket.getWaitListCode(),
                            ticket.getDateTime(),
                            entry.getKey().getId(),
                            entry.getKey().getName(),
                            null,
                            ticket.getCounter() == null ? null : ticket.getCounter().getId()
                    ))
                    .collect(Collectors.toList()));
            return queueDTO;
        }).collect(Collectors.toList());
    }

    public NextCustomerDTO nextCustomer(Long counterId) {
        var todayStartingTime = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        Optional<TicketEntity> optTicket = ticketRepository.findFirstByCounterIdAndIsServedFalse(counterId);
        if (optTicket.isPresent()) {
            optTicket.get().setIsServed(true);
            ticketRepository.save(optTicket.get());
        }
        var optCounter  = counterRepository.findById(counterId);
        if (optCounter.isEmpty()) {
            throw new RuntimeException("Counter not found");
        }
        var counter = optCounter.get();

        Sort sortAsc = Sort.by(Sort.Direction.ASC, "dateTime");
        var tickets = ticketRepository
                .findAllByServiceIdInAndIsServedFalseAndDateTimeAfterAndCounter(
                        counter.getServices().stream().map(ServiceEntity::getId).toList(), todayStartingTime, null, sortAsc);

        if (tickets.isEmpty()) {
            return null;
        }

        // Group tickets by service
        Map<ServiceEntity, List<TicketEntity>> ticketsByService = tickets.stream()
                .collect(Collectors.groupingBy(TicketEntity::getService));

        // Find the group(s) with the maximum size
        int maxGroupSize = ticketsByService.values().stream()
                .mapToInt(List::size)
                .max()
                .orElse(0);

        // Filter groups with maximum size
        List<Map.Entry<ServiceEntity, List<TicketEntity>>> largestGroups = ticketsByService.entrySet().stream()
                .filter(entry -> entry.getValue().size() == maxGroupSize)
                .toList();

        // If there's only one largest group, return the first ticket from that group
        if (largestGroups.size() == 1) {
            var ticket = largestGroups.get(0).getValue().get(0);
            ticket.setCounter(counter);
            ticketRepository.save(ticket);
            return new NextCustomerDTO(ticket.getWaitListCode());
        }

        // If there are multiple largest groups, find the one with the lowest service time
        ServiceEntity serviceWithLowestDuration = largestGroups.stream()
                .min(Comparator.comparingInt(entry -> entry.getKey().getDurationInMinutes()))
                .map(Map.Entry::getKey)
                .orElseThrow();

        // Return the first ticket from the group with the lowest service time
        var ticket = ticketsByService.get(serviceWithLowestDuration).get(0);
        ticket.setCounter(counter);
        ticketRepository.save(ticket);
        return new NextCustomerDTO(ticket.getWaitListCode());
    }
}
