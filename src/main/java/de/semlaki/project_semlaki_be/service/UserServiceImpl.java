package de.semlaki.project_semlaki_be.service;

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
//    private final RoleService roleService;
//    private final EmailService emailService;

    public UserServiceImpl(UserRepository repository, BCryptPasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
//        this.roleService = roleService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
    }

    @Override
    public void register(User user) {
        user.setId(null);
        user.setPassword(encoder.encode(user.getPassword()));

//        user.setRoles(Set.of(roleService.getRoleUser()));

        repository.save(user);

    }
}
