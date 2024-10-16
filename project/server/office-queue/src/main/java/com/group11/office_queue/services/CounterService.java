package com.group11.office_queue.services;

import com.group11.office_queue.models.NextCustomerDTO;
import com.group11.office_queue.models.ServiceDTO;
import com.group11.office_queue.repos.CountersRepository;
import com.group11.office_queue.repos.ServicesRepository;
import com.group11.office_queue.repos.TicketsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class CounterService {

    private final TicketsRepository ticketRepository;
    private final ServicesRepository serviceRepository;
    private final CountersRepository counterRepository;

    public NextCustomerDTO nextCustomer(String counterId) {
        // temporary return
        return new NextCustomerDTO("FOO-1");
    }
}
