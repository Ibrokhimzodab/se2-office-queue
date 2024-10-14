package com.group11.office_queue.controllers;

import com.group11.office_queue.entity.TicketEntity;
import com.group11.office_queue.service.CustomerService;
import org.springframework.web.bind.annotation.PostMapping;
import reactor.core.publisher.Mono;

public class RootController {

    private CustomerService customerService;

    @PostMapping("/customers/next")
    public Mono<TicketEntity> getNextCustomer() {

        return customerService.getNextCustomer();
    }
}
