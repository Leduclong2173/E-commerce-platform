package com.duclong.ecommerce.service;

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
            // Check User đã có Cart hay chưa nếu chưa thì tạo mới
            Cart cart = this.cartRepository.findByUser(user);

            if(cart == null){
                // Tạo mới cart
                Cart newCart = new Cart();
                newCart.setUser(user);
                newCart.setSum(0);


                cart = this.cartRepository.save(newCart);
            }
            // Lưu cartItem
            // Tìm productByID
            Optional<Product> productOptional = this.productRepository.findById(productId);
            if(productOptional.isPresent()){
                Product realProduct = productOptional.get();

                // Check sản phẩm đã từng được thêm vào giỏ hàng trước đây chưa
                CartItem oldItem = this.cartItemRepository.findByCartAndProduct(cart, realProduct);
                if (oldItem == null){
                CartItem cartItem = new CartItem();
                cartItem.setCart(cart);
                cartItem.setProduct(realProduct);
                cartItem.setPrice(realProduct.getPrice());
                cartItem.setQuantity(0);
                this.cartItemRepository.save(cartItem);

                //Update Sum Cart
                int s = cart.getSum() + 1;
                cart.setSum(s);
                this.cartRepository.save(cart);
                session.setAttribute("sum", s);
                } else {
                    oldItem.setQuantity(oldItem.getQuantity() + 1);
                    this.cartItemRepository.save(oldItem);
                }
            }
        }
    }

    public Cart fetchByUser(User user){
        return this.cartRepository.findByUser(user);
    }

    public void handleRemoveCartItem(Long cartItemId, HttpSession session) {
        Optional<CartItem> cartItemOptional = this.cartItemRepository.findById(cartItemId);
        if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();

            Cart currentCart = cartItem.getCart();
            // delete cart-Item
            this.cartItemRepository.deleteById(cartItemId);

            // update cart
            if (currentCart.getSum() > 1) {
                // update current cart
                int s = currentCart.getSum() - 1;
                currentCart.setSum(s);
                session.setAttribute("sum", s);
                this.cartRepository.save(currentCart);
            } else {
                // delete cart (sum = 1)
                this.cartRepository.deleteById(currentCart.getCart_id());
                session.setAttribute("sum", 0);
            }
        }
    }

}
