package com.duclong.ecommerce.controller.client;

import java.util.ArrayList;
import java.util.List;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.duclong.ecommerce.domain.Cart;
import com.duclong.ecommerce.domain.CartItem;
import com.duclong.ecommerce.domain.User;
import com.duclong.ecommerce.service.CartService;
import com.duclong.ecommerce.service.ProductServices;
import com.duclong.ecommerce.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class HomePageController {

    private final ProductServices productServices;
    private final UserService userService;
    private final CartService cartService;

    public HomePageController(
        ProductServices productServices,
        UserService userService,
        CartService cartService
    ){
        this.productServices = productServices;
        this.userService = userService;
        this.cartService = cartService;
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
    
    @PostMapping("/add-product-to-cart/{id}")
    public String postAddProductToCart(@PathVariable Long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long productID = id;
        String username = (String) session.getAttribute("username");
        this.cartService.handleAddProductToCart(username, productID, session);
        return "redirect:/";
    }
    
    @GetMapping("/cart")
    public String getCartPage(Model model, HttpServletRequest request) {
        User currentUser = new User();
        HttpSession session = request.getSession(false);
        Long id = (Long) session.getAttribute("user_id");      
        currentUser.setUser_id(id);

        Cart cart = this.cartService.fetchByUser(currentUser);

        List<CartItem> cartItems = cart == null ? new ArrayList<CartItem>() : cart.getCartItems();

        double totalPrice = 0;
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getPrice() * cartItem.getQuantity();
        }

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);

        return "client/cart/showCart";
    }

    @PostMapping("/delete-cart-product/{id}")
    public String postDeleteCartItem(@PathVariable Long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long cartItemId = id;
        this.cartService.handleRemoveCartItem(cartItemId, session);
        return "redirect:/cart";
    }

}
