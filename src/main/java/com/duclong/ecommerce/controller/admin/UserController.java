package com.duclong.ecommerce.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.duclong.ecommerce.domain.User;
import com.duclong.ecommerce.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;




@Controller
public class UserController {

    private final UserService userService;

    public UserController (
        UserService userService
    ){
        this.userService = userService;
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
    public String postCreateUser(@ModelAttribute("newUser") User user) {
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
