package de.semlaki.project_semlaki_be.security.sec_controller;

import de.semlaki.project_semlaki_be.security.sec_dto.RefreshRequestDto;
import de.semlaki.project_semlaki_be.security.sec_service.AuthService;
import de.semlaki.project_semlaki_be.domain.entity.User;
import de.semlaki.project_semlaki_be.security.sec_dto.TokenResponseDto;
import jakarta.security.auth.message.AuthException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public TokenResponseDto login(@RequestBody User user) throws AuthException {
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
}
