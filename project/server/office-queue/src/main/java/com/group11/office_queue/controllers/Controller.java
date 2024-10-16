package com.group11.office_queue.controllers;

import com.group11.office_queue.models.NextCustomerDTO;
import com.group11.office_queue.models.ServiceDTO;
import com.group11.office_queue.models.TicketDTO;
import com.group11.office_queue.services.CounterService;
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
    private final CounterService counterService;

    @GetMapping("/ticket")
    public ResponseEntity<TicketDTO> getNewTicket(@RequestParam Long serviceId) {
        return ResponseEntity.ok(ticketService.getNewTicket(serviceId));
    }

    @GetMapping("/services")
    public ResponseEntity<List<ServiceDTO>> getServices() {
        return ResponseEntity.ok(ticketService.getServices());
    }

    @PostMapping(value = "/counters/{counterId}/next")
    public ResponseEntity<NextCustomerDTO> nextCustomer(@PathVariable String counterId) {
        return ResponseEntity.ok(counterService.nextCustomer(counterId));
    }

    @GetMapping("/call/{waitListCode}")
    public ResponseEntity<ServiceDTO> callCustomer(@PathVariable String waitListCode) {
        return ResponseEntity.ok(counterService.callCustomer(waitListCode));
    }


}
