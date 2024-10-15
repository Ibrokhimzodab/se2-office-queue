package com.group11.office_queue.repos;

import com.group11.office_queue.entities.CounterEntity;
import com.group11.office_queue.entities.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface CountersRepository extends JpaRepository<CounterEntity, Long> {

    List<CounterEntity> findAllByServicesIn(Collection<Set<ServiceEntity>> services);
}
