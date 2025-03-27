package de.semlaki.project_semlaki_be.service;

import de.semlaki.project_semlaki_be.domain.dto.RegisterRequestDto;
import de.semlaki.project_semlaki_be.domain.dto.UserResponseDto;
import de.semlaki.project_semlaki_be.domain.entity.User;
import de.semlaki.project_semlaki_be.repository.UserRepository;
import de.semlaki.project_semlaki_be.service.interfaces.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository repository;
    private final BCryptPasswordEncoder encoder;
    private final RoleService roleService;
//    private final EmailService emailService;

    public UserServiceImpl(UserRepository repository, BCryptPasswordEncoder encoder, RoleService roleService) {
        this.repository = repository;
        this.encoder = encoder;
//        this.roleService = roleService;
        this.roleService = roleService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User " + email + " not found"));
    }

    @Override
    public UserResponseDto register(RegisterRequestDto user) {
        User registerdUser = user.from();
        registerdUser.setPassword(encoder.encode(registerdUser.getPassword()));
        registerdUser.getRoles().add(roleService.getUserRole());
        repository.save(registerdUser);
        return UserResponseDto.toDto(registerdUser);
    }
}
