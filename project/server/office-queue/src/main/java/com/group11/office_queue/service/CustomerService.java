package com.group11.office_queue.service;

import com.group11.office_queue.entity.TicketEntity;
import reactor.core.publisher.Mono;

public class CustomerService {

    public Mono<TicketEntity> getNextCustomer() {
        return Mono.just(new TicketEntity());
    }
}
