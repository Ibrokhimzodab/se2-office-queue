package com.group11.office_queue.repos;

import com.group11.office_queue.entities.Counter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountersRepository extends JpaRepository<Counter, Long> {
}
