package com.duclong.ecommerce.controller.client;


import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.duclong.ecommerce.domain.User;
import com.duclong.ecommerce.service.UploadService;
import com.duclong.ecommerce.service.UserService;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class DashboardClientController {

    private final UserService userService;
    private final UploadService uploadService;

    public DashboardClientController (
        UserService userService,
        UploadService uploadService
    ){
        this.userService = userService;
        this.uploadService = uploadService;
    }

    @GetMapping("/client")
    public String getDashboardPage(Model model, HttpServletRequest request ) {
        return "client/dashboard/showDashboard";
    }

    @GetMapping("/client/information")
    public String getIformationUserPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long id = (Long) session.getAttribute("user_id");
        User user = this.userService.getUserById(id);
        model.addAttribute("inforUser", user);
        return "client/dashboard/information/showInformation";
    }

    @PostMapping("/client/information/update")
    public String postUpdateInformationUser(
        @Valid @ModelAttribute("inforUser") User user,
        BindingResult updateUserBindingResult,
        @RequestParam("avatarFile") MultipartFile file
        ) {
        
        List<FieldError> errors = updateUserBindingResult.getFieldErrors();
        for (FieldError error : errors ) {
        System.out.println (error.getField() + " - " + error.getDefaultMessage());
        }

        // Validate
         User userByUsername = this.userService.getUserByUsername(user.getUsername());
        if (userByUsername != null && userByUsername.getUser_id() != userByUsername.getUser_id()) {
            updateUserBindingResult.rejectValue("email", "error.email", "Email đã tồn tại!");
        }

        User userByEmail = this.userService.getUserByEmail(user.getEmail());
        if (userByEmail != null && userByEmail.getUser_id() != userByEmail.getUser_id()) {
        updateUserBindingResult.rejectValue("email", "error.email", "Email đã tồn tại!");
        }

        if(updateUserBindingResult.hasErrors()){
            return "client/dashboard/information/showInformation";
        }

        String avatarName = this.uploadService.handleUploadFile(file, "avatar");

        user.setAvatar(avatarName);
        user.setRole(this.userService.getRoleByName(user.getRole().getName()));

        this.userService.handleSaveUser(user);
        return "redirect:/client/information";
    }
}
