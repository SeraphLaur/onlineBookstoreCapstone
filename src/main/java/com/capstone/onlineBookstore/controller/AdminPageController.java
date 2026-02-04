package com.capstone.onlineBookstore.controller;

import com.capstone.onlineBookstore.dto.RegisterRequest;
import com.capstone.onlineBookstore.model.Role;
import com.capstone.onlineBookstore.model.User;
import com.capstone.onlineBookstore.repository.RoleRepository;
import com.capstone.onlineBookstore.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AdminPageController {

    private final RegistrationService registrationService;
    private final RoleRepository roleRepository;

    public AdminPageController(RegistrationService registrationService,
                               RoleRepository roleRepository) {
        this.registrationService = registrationService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/admin")
    public String ordersPage() {

        return "adminBooks";
    }

    @GetMapping("/admin/register-user")
    public String showRegisterUserForm(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("roles", roles);
        return "adminRegister";
    }

    /**
     * Handle admin user registration

     */

    @PostMapping("/admin/register-user")
    public ModelAndView registerUser(@Valid @ModelAttribute RegisterRequest registerRequest) {
        try {
            User newUser = registrationService.registerWithRole(registerRequest);
            return new ModelAndView("redirect:/admin?registered=" + newUser.getEmail());
        } catch (IllegalArgumentException e) {
            return new ModelAndView("redirect:/admin/register-user?error=" + e.getMessage());
        }
    }

}