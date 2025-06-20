package com.duclong.ecommerce.controller.client;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.duclong.ecommerce.domain.User;
import com.duclong.ecommerce.service.UserService;

import jakarta.validation.Valid;


@Controller
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(
        UserService userService,
        PasswordEncoder passwordEncoder){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    
    @GetMapping("/register")
    public String getRegister(Model model) {
        model.addAttribute("newUser", new User());
        return "client/auth/register";
    }

    @PostMapping("/register")
    public String postRegister(
        @Valid @ModelAttribute("newUser") User user,
        BindingResult newUserBindingResult
        ) {
        
        List<FieldError> errors = newUserBindingResult.getFieldErrors();
        for (FieldError error : errors ) {
        System.out.println (error.getField() + " - " + error.getDefaultMessage());
        }

        // Validate
        if (this.userService.checkUsernameExist(user.getUsername())) {
        newUserBindingResult.rejectValue("username", "error.username", "Username đã tồn tại!");
        }

        if (this.userService.checkEmailExist(user.getEmail())) {
        newUserBindingResult.rejectValue("email", "error.email", "Email đã tồn tại!");
        }

        if(newUserBindingResult.hasErrors()){
            return "client/auth/register";
        }

        String hashPassword = this.passwordEncoder.encode(user.getPassword());

        user.setPassword(hashPassword);
        user.setRole(this.userService.getRoleByName("USER"));

        this.userService.handleSaveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "client/auth/login";
    }

    @GetMapping("/access-deny")
    public String getDenyPage() {
        return "client/auth/deny";
    }
}
