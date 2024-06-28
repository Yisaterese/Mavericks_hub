package com.maverickstube.maverickshub.repository;

import com.maverickstube.maverickshub.data.model.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaRepository extends JpaRepository<Media,Long> {
    @Query("SELECT m FROM Media m WHERE m.uploaderId =:userId ")
    List<Media> findAllMediaFor(Long userId);
}
