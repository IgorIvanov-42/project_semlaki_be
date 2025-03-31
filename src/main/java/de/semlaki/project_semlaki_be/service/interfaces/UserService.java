package de.semlaki.project_semlaki_be.service.interfaces;

import de.semlaki.project_semlaki_be.domain.dto.RegisterRequestDto;
import de.semlaki.project_semlaki_be.domain.dto.UserResponseDto;
import de.semlaki.project_semlaki_be.domain.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserResponseDto register(RegisterRequestDto user);



}
