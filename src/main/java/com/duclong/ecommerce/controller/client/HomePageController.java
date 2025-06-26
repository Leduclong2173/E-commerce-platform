package com.duclong.ecommerce.controller.client;

import java.util.ArrayList;
import java.util.List;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.duclong.ecommerce.domain.User;
import com.duclong.ecommerce.service.ProductServices;
import com.duclong.ecommerce.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class HomePageController {

    private final ProductServices productServices;
    private final UserService userService;

    public HomePageController(
        ProductServices productServices,
        UserService userService
    ){
        this.productServices = productServices;
        this.userService = userService;
    }
    
    @GetMapping("/")
    public String getHomePage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long currentUserId = null;
        if (session != null) {
            currentUserId = (Long) session.getAttribute("user_id");
        }

        List<User> users = this.userService.getAllUser();
        List<User> usersWithProducts = new ArrayList<>();
        for (User user : users) {
            if (!productServices.getAllProduct(user).isEmpty() && 
                (currentUserId == null || !user.getUser_id().equals(currentUserId))) {
                usersWithProducts.add(user);
            }
        }
        model.addAttribute("users", usersWithProducts);
        return "client/homepage/showHomePage";
    }
    
    // @PostMapping("/add-product-to-cart/{id}")
    // public String postAddProductToCart(
    //     @PathVariable long id,
    //     HttpServletRequest request) {
    //     HttpSession session = request.getSession(false);
    //     Long productId = id;
    //     String username = (String) session.getAttribute("username");
    //     this.cartServices.handleAddProductToCart(username, productId, session);
    //     return "redirect:/";
    // }
    
    // @GetMapping("/cart")
    // public String getCartPage(Model model) {
    //     return "client/cart/showCart";
    // }
}
