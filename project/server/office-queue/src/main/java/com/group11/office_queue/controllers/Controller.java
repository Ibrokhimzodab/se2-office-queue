package com.group11.office_queue.controllers;

import com.group11.office_queue.models.NextCustomerDTO;
import com.group11.office_queue.models.QueueDTO;
import com.group11.office_queue.models.ServiceDTO;
import com.group11.office_queue.models.TicketDTO;
import com.group11.office_queue.services.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping()
public class Controller {

    private final TicketService ticketService;

    @GetMapping("/ticket")
    public ResponseEntity<TicketDTO> getNewTicket(@RequestParam Long serviceId) {
        return ResponseEntity.ok(ticketService.getNewTicket(serviceId));
    }

    @GetMapping("/services")
    public ResponseEntity<List<ServiceDTO>> getServices() {
        return ResponseEntity.ok(ticketService.getServices());
    }

    @PostMapping(value = "/counters/{counterId}/next")
    public ResponseEntity<NextCustomerDTO> nextCustomer(@PathVariable Long counterId) {
        return ResponseEntity.ok(ticketService.nextCustomer(counterId));
    }

    @GetMapping("/queues")
    public ResponseEntity<List<QueueDTO>> getAllTicketsInQueues() {
        return ResponseEntity.ok(ticketService.getAllTicketsInQueues());
    }
}
