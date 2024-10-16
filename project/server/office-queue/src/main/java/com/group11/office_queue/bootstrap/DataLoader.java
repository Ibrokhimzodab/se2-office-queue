package com.group11.office_queue.bootstrap;

import com.group11.office_queue.entities.CounterEntity;
import com.group11.office_queue.entities.ServiceEntity;
import com.group11.office_queue.repos.CountersRepository;
import com.group11.office_queue.repos.ServicesRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class DataLoader implements CommandLineRunner {
    private final CountersRepository countersRepository;
    private final ServicesRepository servicesRepository;

    public DataLoader(CountersRepository countersRepository, ServicesRepository servicesRepository) {
        this.countersRepository = countersRepository;
        this.servicesRepository = servicesRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        ServiceEntity parcelsService = servicesRepository.save(new ServiceEntity("parcels", 10));
        ServiceEntity billsService = servicesRepository.save(new ServiceEntity("bills", 2));
        ServiceEntity paymentsService = servicesRepository.save(new ServiceEntity("payments", 5));

        CounterEntity counter1 = countersRepository.save(new CounterEntity("counter1"));
        CounterEntity counter2 = countersRepository.save(new CounterEntity("counter2"));
        CounterEntity counter3 = countersRepository.save(new CounterEntity("counter3"));

        countersRepository.save(new CounterEntity());
    }
}

