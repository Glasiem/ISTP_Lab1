package com.glasiem.repository;

import com.glasiem.entity.AgencyEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgencyRepo extends CrudRepository<AgencyEntity, Long> {

        AgencyEntity findByName(String name);
}
