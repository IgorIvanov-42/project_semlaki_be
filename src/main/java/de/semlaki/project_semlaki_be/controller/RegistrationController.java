package de.semlaki.project_semlaki_be.controller;

import de.semlaki.project_semlaki_be.domain.entity.User;
import de.semlaki.project_semlaki_be.service.interfaces.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    private final UserService service;

    public RegistrationController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public String register(@RequestBody User user) {
        service.register(user);
//        return new Response("Регистрация успешна. Проверьте почту для подтверждения регистрации.");
        return "Registered Successfully";
    }
}