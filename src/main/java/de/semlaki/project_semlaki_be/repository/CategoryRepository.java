package de.semlaki.project_semlaki_be.repository;

import de.semlaki.project_semlaki_be.domain.entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Categories, Long> {
}
