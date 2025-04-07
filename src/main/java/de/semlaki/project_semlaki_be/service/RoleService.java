package de.semlaki.project_semlaki_be.service;

import de.semlaki.project_semlaki_be.domain.entity.Role;
import de.semlaki.project_semlaki_be.exception.RestApiException;
import de.semlaki.project_semlaki_be.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    public static final String USER_ROLE_NAME = "ROLE_USER";
    public static final String ADMIN_ROLE_NAME = "ROLE_ADMIN";

    public Role userRole;

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    private void init() {
        this.userRole = getOrCreate(USER_ROLE_NAME);
        getOrCreate(ADMIN_ROLE_NAME);
    }

    private Role getOrCreate(String roleName) {
        return getRole(roleName)
                .orElseGet(() -> roleRepository.save(new Role(roleName)));
    }

    public Role getRoleOrThrow(String roleName) {
        return getRole(roleName)
                .orElseThrow(() -> new RestApiException("Role not found"));
    }

    public Optional<Role> getRole(String roleName) {
        return roleRepository.findByTitle(roleName);
    }

    public Role getUserRole() {
        return userRole;
    }
}
