package com.group11.office_queue.repos;

import com.group11.office_queue.entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicesRepository extends JpaRepository<Service, Long> {
}
