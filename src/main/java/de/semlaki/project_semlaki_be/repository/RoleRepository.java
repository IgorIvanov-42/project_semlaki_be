package de.semlaki.project_semlaki_be.repository;

import de.semlaki.project_semlaki_be.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByTitle(String title);
}
