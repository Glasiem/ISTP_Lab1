package com.glasiem.repository;

import com.glasiem.entity.MediaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepo extends CrudRepository<MediaEntity,Long> {
    Iterable<MediaEntity> deleteAllByVtuber_Id(Long id);

    Iterable<MediaEntity> findAllByVtuber_Id(Long id);
}
