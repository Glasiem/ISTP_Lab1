package com.glasiem.repository;

import com.glasiem.entity.ManagerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepo extends CrudRepository<ManagerEntity, Long> {

    ManagerEntity findByName(String name);

    ManagerEntity findByAgency_Id(Long id);

    void deleteByAgency_Id(Long id);
    Iterable<ManagerEntity> findAllByAgency_Id(Long id);
}
