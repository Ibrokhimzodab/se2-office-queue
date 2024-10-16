package com.group11.office_queue.bootstrap;

import com.group11.office_queue.entities.CounterEntity;
import com.group11.office_queue.entities.ServiceEntity;
import com.group11.office_queue.repos.CountersRepository;
import com.group11.office_queue.repos.ServicesRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DataLoader implements CommandLineRunner {
    private final CountersRepository countersRepository;
    private final ServicesRepository servicesRepository;

    public DataLoader(CountersRepository countersRepository, ServicesRepository servicesRepository) {
        this.countersRepository = countersRepository;
        this.servicesRepository = servicesRepository;
    }

    @Override
    public void run(String... args) {
        if (countersRepository.count() == 0 && servicesRepository.count() == 0) {
            loadData();
        }
    }

    private void loadData() {
        // Create 5 counters
        List<CounterEntity> counters = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            CounterEntity counter = new CounterEntity();
            counter.setName("Counter " + i);
            counters.add(counter);
        }

        // Create 5 services
        List<ServiceEntity> services = new ArrayList<>();
        String[] serviceNames = {"Passport Application", "Visa Renewal", "ID Card Issuance", "Address Change", "Document Certification"};
        for (int i = 0; i < 5; i++) {
            ServiceEntity service = new ServiceEntity();
            service.setName(serviceNames[i]);
            service.setDurationInMinutes(2 + i); // 1, 3, 4, 5, 6 minutes
            services.add(service);
        }
        services = servicesRepository.saveAll(services);

        // Randomly assign counters to services
        Random random = new Random();
        for (ServiceEntity service : services) {
            int numCounters = random.nextInt(2) + 1; // Assign 1 or 2 counters
            Set<CounterEntity> assignedCounters = new HashSet<>();
            while (assignedCounters.size() < numCounters) {
                CounterEntity counter = counters.get(random.nextInt(counters.size()));
                if (assignedCounters.add(counter)) {
                    counter.getServices().add(service);
                }
            }
        }
        countersRepository.saveAll(counters); // Save counters again to update their services
    }
}