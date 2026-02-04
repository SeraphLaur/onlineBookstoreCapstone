package com.capstone.onlineBookstore.service;

import com.capstone.onlineBookstore.dto.RegisterRequest;
import com.capstone.onlineBookstore.model.Role;
import com.capstone.onlineBookstore.model.User;
import com.capstone.onlineBookstore.repository.RoleRepository;
import com.capstone.onlineBookstore.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type Registration service.
 */
@Service
public class RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    /**
     * Instantiates a new Registration service.
     *
     * @param userRepository  the user repository
     * @param passwordEncoder the password encoder
     */
    public RegistrationService(UserRepository userRepository,
                               RoleRepository roleRepository,
                               PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Register user.
     *
     * @param req the req
     * @return the user
     */
    @Transactional
    public User register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        User u = new User();
        u.setFirstName(req.getFirstName());
        u.setLastName(req.getLastName());
        u.setEmail(req.getEmail());
        u.setHashedPassword(passwordEncoder.encode(req.getPassword()));

        Role userRole = roleRepository.findByRoleName("ROLE_USER")
                .orElseThrow(() -> new IllegalStateException("Default USER role not found"));
        u.setRole(userRole);

        return userRepository.save(u);
    }

    @Transactional
    public User registerWithRole(RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        User u = new User();
        u.setFirstName(req.getFirstName());
        u.setLastName(req.getLastName());
        u.setEmail(req.getEmail());
        u.setHashedPassword(passwordEncoder.encode(req.getPassword()));


        Role role;
        if (req.getRoleId() != null) {
            role = roleRepository.findById(req.getRoleId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid role ID"));
        } else {
            role = roleRepository.findByRoleName("ROLE_USER")
                    .orElseThrow(() -> new IllegalStateException("Default USER role not found"));
        }
        u.setRole(role);

        return userRepository.save(u);
    }
}
