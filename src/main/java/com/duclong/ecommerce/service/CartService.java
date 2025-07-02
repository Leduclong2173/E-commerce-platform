package com.duclong.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.duclong.ecommerce.domain.Cart;
import com.duclong.ecommerce.domain.CartItem;
import com.duclong.ecommerce.domain.Product;
import com.duclong.ecommerce.domain.User;
import com.duclong.ecommerce.repository.CartItemRepository;
import com.duclong.ecommerce.repository.CartRepository;
import com.duclong.ecommerce.repository.ProductRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class CartService {
    
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserService userService;
    private final ProductRepository productRepository;

    public CartService(
        CartRepository cartRepository,
        CartItemRepository cartItemRepository,
        UserService userService,
        ProductRepository productRepository
    ){
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userService = userService;
        this.productRepository = productRepository;
    }
    public void handleAddProductToCart(String username, Long productId, HttpSession session){
        User user = this.userService.getUserByUsername(username);
        if(user != null){
            // Check user have cart
            Cart cart = this.cartRepository.findByUser(user);

            if(cart == null){
                // Create new cart
                Cart newCart = new Cart();
                newCart.setUser(user);
                newCart.setSum(0);

                cart = this.cartRepository.save(newCart);
            }
            // Save cart items
            // Search product by id
            Optional<Product> productOptional = this.productRepository.findById(productId);
            if(productOptional.isPresent()){
                Product realProduct = productOptional.get();

                // Check product isExist in cart before
                CartItem oldCartItem = this.cartItemRepository.findByCartAndProduct(cart, realProduct);
                if(oldCartItem == null){
                    CartItem cartItem = new CartItem();
                    cartItem.setCart(cart);
                    cartItem.setProduct(realProduct);
                    cartItem.setPrice(realProduct.getPrice());
                    cartItem.setQuantity(1);
                    this.cartItemRepository.save(cartItem);
                    // Update sum cart item in cart
                    int sum = cart.getSum() + 1;
                    cart.setSum(sum);
                    this.cartRepository.save(cart);
                    session.setAttribute("sum", sum);
                } else {
                    oldCartItem.setQuantity(oldCartItem.getQuantity() + 1);
                    this.cartItemRepository.save(oldCartItem);
                }
                
            } 
        }
    }

    public Cart fetchByUser(User user){
        return this.cartRepository.findByUser(user);
    }

    public void handleRemoveCartItem(Long cartItemId, HttpSession session){
        Optional<CartItem> cartItemOptional = this.cartItemRepository.findById(cartItemId);
        if(cartItemOptional.isPresent()){
            CartItem cartItem = cartItemOptional.get();

            Cart currenCart = cartItem.getCart();
            // Delete cart item
            this.cartItemRepository.deleteById(cartItemId);

            // Update sum cart item in cart
            if(currenCart.getSum() > 1){
                // Update sum
                int sum = currenCart.getSum() - 1;
                currenCart.setSum(sum);
                this.cartRepository.save(currenCart);
                session.setAttribute("sum", sum);
            } else {
                // Delete if sum = 1
                this.cartRepository.deleteById(currenCart.getCart_id());
                session.setAttribute("sum", 0);
            }
        }
    }

    public void handleUpdateCartBeforeCheckout(List<CartItem> cartItems){
        for (CartItem cartItem : cartItems) {
            Optional<CartItem> cartItemOptional = this.cartItemRepository.findById(cartItem.getCart_item_id());
            if(cartItemOptional.isPresent()){
                CartItem currentCartItem = cartItemOptional.get();
                currentCartItem.setQuantity(cartItem.getQuantity());
                this.cartItemRepository.save(currentCartItem);
            }
        }
    }
}
