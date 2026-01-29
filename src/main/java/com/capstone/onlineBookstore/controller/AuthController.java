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
        // On success, redirect to login with a flash-style query param
        return new ModelAndView("redirect:/login?registered=" + created.getEmail());
    }

    @GetMapping("/login")
    public ModelAndView loginPage() {
        return new ModelAndView("login"); // renders the view instead of writing "login"
    }

    @GetMapping("/register")
    public ModelAndView registerPage() {
        return new ModelAndView("register");
    }




    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req) {
        User created = registrationService.register(req);
        return ResponseEntity.ok().body(
                new SimpleMessage("Registered successfully with email " + created.getEmail())
        );
    }



    // Simple DTO for messages
    static class SimpleMessage {
        public String message;
        public SimpleMessage(String m) { this.message = m; }
    }
}
