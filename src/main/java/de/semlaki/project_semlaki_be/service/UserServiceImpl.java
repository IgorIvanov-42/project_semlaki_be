package de.semlaki.project_semlaki_be.service;

import de.semlaki.project_semlaki_be.repository.UserRepository;
import de.semlaki.project_semlaki_be.service.interfaces.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
    }
}
