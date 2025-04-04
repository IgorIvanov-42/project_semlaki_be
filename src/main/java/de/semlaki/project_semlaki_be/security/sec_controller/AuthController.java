package de.semlaki.project_semlaki_be.security.sec_controller;

import de.semlaki.project_semlaki_be.domain.dto.UserResponseDto;
import de.semlaki.project_semlaki_be.security.sec_dto.LoginRequestDto;
import de.semlaki.project_semlaki_be.security.sec_dto.RefreshRequestDto;
import de.semlaki.project_semlaki_be.security.sec_service.AuthService;
import de.semlaki.project_semlaki_be.domain.entity.User;
import de.semlaki.project_semlaki_be.security.sec_dto.TokenResponseDto;
import de.semlaki.project_semlaki_be.service.interfaces.UserService;
import jakarta.security.auth.message.AuthException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;
    private final UserService userService;

    public AuthController(AuthService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @PostMapping("/login")
    public TokenResponseDto login(@RequestBody LoginRequestDto user) throws AuthException {
        return service.login(user);
    }

    @PostMapping("/refresh")
    public TokenResponseDto getNewAccessToken(@RequestBody RefreshRequestDto refreshRequest) {
        return service.getNewAccessToken(refreshRequest.getRefreshToken());
    }

    @GetMapping("/health")
    public String getHealth(){
        return "Is healthy";
    }
    @GetMapping("/profile")
    public UserResponseDto getProfile(){
        return userService.findCurrentUser();
    }


}
