package com.glasiem.repository;

import com.glasiem.entity.GenerationEntity;
import com.glasiem.entity.VTuberEntity;
import com.glasiem.model.Generation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
public interface VTuberRepo extends CrudRepository<VTuberEntity, Long> {
    VTuberEntity findByName(String name);

    Iterable<VTuberEntity> findAllByGeneration_Id(Long id);

    Iterable<VTuberEntity> deleteAllByGeneration_Id(Long id);

    int countAllByGenerationIn(Iterable<GenerationEntity> generations);
}
