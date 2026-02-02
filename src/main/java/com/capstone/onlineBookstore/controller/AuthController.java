package com.capstone.onlineBookstore.controller;

import com.capstone.onlineBookstore.dto.RegisterRequest;
import com.capstone.onlineBookstore.model.User;
import com.capstone.onlineBookstore.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class AuthController {

    private final RegistrationService registrationService;

    public AuthController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping(value = "/register", consumes = "application/x-www-form-urlencoded")
    public ModelAndView registerFromForm(@Valid RegisterRequest req) {
        User created = registrationService.register(req);
        return new ModelAndView("redirect:/login?registered=" + created.getEmail());
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req) {
        User created = registrationService.register(req);
        return ResponseEntity.ok().body(
                new SimpleMessage("Registered successfully with email " + created.getEmail())
        );
    }


    static class SimpleMessage {
        public String message;
        public SimpleMessage(String m) { this.message = m; }
    }
}
