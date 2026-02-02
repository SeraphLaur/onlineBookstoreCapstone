package com.capstone.onlineBookstore.controller;

import com.capstone.onlineBookstore.dto.RegisterRequest;
import com.capstone.onlineBookstore.model.User;
import com.capstone.onlineBookstore.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * The type Auth controller.
 */
@RestController
public class AuthController {

    private final RegistrationService registrationService;

    /**
     * Instantiates a new Auth controller.
     *
     * @param registrationService the registration service
     */
    public AuthController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    /**
     * Register from form model and view.
     *
     * @param req the req
     * @return the model and view
     */
    @PostMapping(value = "/register", consumes = "application/x-www-form-urlencoded")
    public ModelAndView registerFromForm(@Valid RegisterRequest req) {
        User created = registrationService.register(req);
        return new ModelAndView("redirect:/login?registered=" + created.getEmail());
    }


    /**
     * Register response entity.
     *
     * @param req the req
     * @return the response entity
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req) {
        User created = registrationService.register(req);
        return ResponseEntity.ok().body(
                new SimpleMessage("Registered successfully with email " + created.getEmail())
        );
    }


    /**
     * The type Simple message.
     */
    static class SimpleMessage {
        /**
         * The Message.
         */
        public String message;

        /**
         * Instantiates a new Simple message.
         *
         * @param m the m
         */
        public SimpleMessage(String m) { this.message = m; }
    }
}
