package de.semlaki.project_semlaki_be.repository;

import de.semlaki.project_semlaki_be.domain.entity.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Services, Long> {
    List<Services> findByCategoryId(Long categoryId);
    List<Services> findByUserId(Long userId);

    @Query(value = """
    SELECT * FROM services s 
    WHERE s.title LIKE CONCAT('%', ?1, '%') 
    OR s.description LIKE CONCAT('%', ?1, '%')
    """, nativeQuery = true)
    List<Services> findAllByDescriptionOrTitle(String keyword);




}
