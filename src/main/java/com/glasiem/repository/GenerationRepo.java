package com.glasiem.repository;

import com.glasiem.entity.GenerationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenerationRepo extends CrudRepository<GenerationEntity, Long> {
    GenerationEntity findByName(String name);

    Iterable<GenerationEntity> findAllByAgency_Id(Long id);

    Iterable<GenerationEntity> deleteAllByAgency_Id(Long id);
}
