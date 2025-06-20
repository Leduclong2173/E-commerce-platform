package com.duclong.ecommerce.controller.admin;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.duclong.ecommerce.domain.User;
import com.duclong.ecommerce.service.UploadService;
import com.duclong.ecommerce.service.UserService;

import jakarta.servlet.ServletContext;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;




@Controller
public class UserController {

    private final UserService userService;
    private final UploadService uploadService;
    private final PasswordEncoder passwordEncoder;

    public UserController (
        UserService userService,
        UploadService uploadService,
        PasswordEncoder passwordEncoder
    ){
        this.userService = userService;
        this.uploadService = uploadService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/admin/user")
    public String getUserPage(Model model) {
        List<User> users = this.userService.getAllUser();
        model.addAttribute("users", users);
        return "admin/manageuser/showUser";
    }
    
    @GetMapping("/admin/user/create")
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/manageuser/createUser";
    }
    
    @PostMapping("/admin/user/create")
    public String postCreateUser(
        @Valid @ModelAttribute("newUser") User user,
        BindingResult newUserBindingResult,
        @RequestParam("avatarFile") MultipartFile file
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
            return "admin/manageuser/createUser";
        }

        String avatarName = this.uploadService.handleUploadFile(file, "avatar");
        String hashPassword = this.passwordEncoder.encode(user.getPassword());

        user.setAvatar(avatarName);
        user.setPassword(hashPassword);
        user.setRole(this.userService.getRoleByName(user.getRole().getName()));

        this.userService.handleSaveUser(user);
        return "redirect:/admin/user";
    }
    
    @GetMapping("/admin/user/delete/{id}")
    public String getDeleteUserPage(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        model.addAttribute("newUser", new User());
        return "admin/manageuser/deleteUser";
    }
    
    @PostMapping("/admin/user/delete")
    public String postDeleteUser(@ModelAttribute("newUser") User user) {
        this.userService.deleteUserById(user.getUser_id());
        return "redirect:/admin/user";
    }
}
