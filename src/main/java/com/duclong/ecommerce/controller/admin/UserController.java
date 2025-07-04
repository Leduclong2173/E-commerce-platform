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

import com.duclong.ecommerce.domain.Product;
import com.duclong.ecommerce.domain.User;
import com.duclong.ecommerce.service.ProductServices;
import com.duclong.ecommerce.service.UploadService;
import com.duclong.ecommerce.service.UserService;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;




@Controller
public class UserController {

    private final UserService userService;
    private final UploadService uploadService;
    private final PasswordEncoder passwordEncoder;
    private final ProductServices productServices;

    public UserController (
        UserService userService,
        UploadService uploadService,
        PasswordEncoder passwordEncoder,
        ProductServices productServices
    ){
        this.userService = userService;
        this.uploadService = uploadService;
        this.passwordEncoder = passwordEncoder;
        this.productServices = productServices;
    }

    @GetMapping("/admin/user")
    public String getUserPage(Model model) {
        List<User> users = this.userService.getAllUser();
        model.addAttribute("users", users);
        return "admin/manageuser/showUser";
    }

    @GetMapping("/admin/user/search")
    public String getSearchUser(@RequestParam("keyword") String keyword, Model model) {
        List<User> users = userService.searchByUsernameOrId(keyword);
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
        @RequestParam("avatarFile") MultipartFile file,
        RedirectAttributes redirectAttributes,
        HttpServletRequest request
        ) {

        HttpSession session = request.getSession(false);
        
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

        try {
            this.userService.handleSaveUser(user);
            redirectAttributes.addFlashAttribute("message", "Thêm người dùng thành công!");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Lỗi khi thêm người dùng: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
        }
        
        session.setAttribute("avatar", avatarName);
        return "redirect:/admin/user";
    }
    
    @GetMapping("/admin/user/delete/{id}")
    public String getDeleteUserPage(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        model.addAttribute("newUser", new User());
        return "admin/manageuser/deleteUser";
    }
    
    @PostMapping("/admin/user/delete")
    public String postDeleteUser(@ModelAttribute("newUser") User user, RedirectAttributes redirectAttributes) {
        try {
            this.userService.deleteUserById(user.getUser_id());
            redirectAttributes.addFlashAttribute("message", "Xóa người dùng thành công!");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Lỗi khi xóa người dùng: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
        }
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/{id}")
    public String getDetailUserPage(Model model, @PathVariable long id) {
        User user = this.userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("id", id);
        return "admin/manageuser/detailUser";
    }

    @GetMapping("/admin/user/update/{id}")
    public String getUpdateUserPage(Model model, @PathVariable long id) {
        User updateUser = this.userService.getUserById(id);
        model.addAttribute("updateUser", updateUser);
        return "admin/manageuser/updateUser";
    }
    
    @PostMapping("/admin/user/update")
    public String postUpdateUser(
        @Valid @ModelAttribute("updateUser") User user,
        BindingResult updateUserBindingResult,
        @RequestParam("avatarFile") MultipartFile file,
        RedirectAttributes redirectAttributes,
        HttpServletRequest request
        ) {
        
        HttpSession session = request.getSession(false);
        List<FieldError> errors = updateUserBindingResult.getFieldErrors();
        for (FieldError error : errors ) {
        System.out.println (error.getField() + " - " + error.getDefaultMessage());
        }

        // Validate
        User userByEmail = userService.getUserByEmail(user.getEmail());
        if (userByEmail != null && !userByEmail.getUser_id().equals(user.getUser_id())) {
            updateUserBindingResult.rejectValue("email", "error.email", "Email đã tồn tại!");
        }
        if(updateUserBindingResult.hasErrors()){
            return "admin/manageuser/updateUser";
        }

        String avatarName = this.uploadService.handleUploadFile(file, "avatar");

        user.setAvatar(avatarName);
        user.setRole(this.userService.getRoleByName(user.getRole().getName()));

        try {
            this.userService.handleSaveUser(user);
            redirectAttributes.addFlashAttribute("message", "Cập nhật thông tin người dùng thành công!");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Lỗi khi cập nhật thông tin người dùng: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
        }
        
        session.setAttribute("avatar", avatarName);
        return "redirect:/admin/user";
    }
    
    // @GetMapping("/admin/user/viewshop/{id}")
    // public String getViewShopPage(Model model, @PathVariable long id) {
    //     User user = this.userService.getUserById(id);
    //     List<Product> products = this.productServices.getAllProductByUser(user);
    //     model.addAttribute("products", products);
    //     return "admin/manageuser/viewShop";
    // }

    // @GetMapping("/admin/user/viewshop/search")
    // public String getsearchProduct(@RequestParam("keyword") String keyword, Model model) {
    //     List<Product> products = this.productServices.searchByNameOrId(keyword);
    //     model.addAttribute("products", products);
    //     return "admin/manageuser/viewShop";
    // }

    // @GetMapping("/admin/user/viewshopdetail/{id}")
    // public String getViewShopDetailPage(Model model, @PathVariable long id) {
    //     Product product = this.productServices.getProductById(id);
    //     model.addAttribute("product", product);
    //     model.addAttribute("id", id);
    //     return "admin/manageuser/viewShopDetail";
    // }
}
