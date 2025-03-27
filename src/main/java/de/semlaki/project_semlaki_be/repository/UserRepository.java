package de.semlaki.project_semlaki_be.repository;

import de.semlaki.project_semlaki_be.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
