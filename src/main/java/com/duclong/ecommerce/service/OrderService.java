package com.duclong.ecommerce.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.duclong.ecommerce.domain.Cart;
import com.duclong.ecommerce.domain.CartItem;
import com.duclong.ecommerce.domain.Order;
import com.duclong.ecommerce.domain.OrderItem;
import com.duclong.ecommerce.domain.Product;
import com.duclong.ecommerce.domain.User;
import com.duclong.ecommerce.repository.CartItemRepository;
import com.duclong.ecommerce.repository.CartRepository;
import com.duclong.ecommerce.repository.OrderItemRepository;
import com.duclong.ecommerce.repository.OrderRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class OrderService {

    private final CartItemRepository cartItemRepository;
    
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;

    public OrderService(
        OrderRepository orderRepository,
        OrderItemRepository orderItemRepository,
        CartRepository cartRepository
    , CartItemRepository cartItemRepository){
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public void handlePlaceOrder(
        User user, HttpSession session,
        String receiverName,
        String receiverAddress,
        String receiverPhone){

            // Create order
            Order order = new Order();
            order.setUser(user);
            order.setReceiverName(receiverName);
            order.setReceiverAddress(receiverAddress);
            order.setReceiverPhone(receiverPhone);
            order.setCreateAt(new Date());
            order.setStatus("PENDING");
            order = this.orderRepository.save(order);

            // Create order item
            Cart cart = this.cartRepository.findByUser(user);
            if(cart != null){
                List<CartItem> cartItems = cart.getCartItems();

                if(cartItems != null){
                    for (CartItem cartItem : cartItems) {
                        OrderItem orderItem = new OrderItem();
                        orderItem.setOrder(order);
                        orderItem.setProduct(cartItem.getProduct());
                        orderItem.setPrice(cartItem.getPrice());
                        orderItem.setQuantity(cartItem.getQuantity());
                        this.orderItemRepository.save(orderItem);
                    }
                    // Update total price
                    double totalPrice = 0;
                    for (CartItem cartItem : cartItems) {
                        totalPrice += cartItem.getPrice() * cartItem.getQuantity();
                    }
                    order.setTotalPrice(totalPrice);
                    // Update stock
                    for (CartItem cartItem : cartItems) {
                        Product product = cartItem.getProduct();
                        Long newStock = product.getStock() - cartItem.getQuantity();
                        product.setStock(newStock);
                    }

                    // Delete cart item and cart sum
                    for (CartItem cartItem : cartItems) {
                        this.cartItemRepository.deleteById(cartItem.getCart_item_id());
                    }

                    this.cartRepository.deleteById(cart.getCart_id());

                    // Update session
                    session.setAttribute("sum", 0);
                }
            }
    }

    public List<Order> getAllOrder(){
        return this.orderRepository.findAll();
    }

    public Optional<Order> fetchOrderById(long id) {
        return this.orderRepository.findById(id);
    }

    public void deleteOrderById(long id) {
        // delete order item
        Optional<Order> orderOptional = this.fetchOrderById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            List<OrderItem> orderItems = order.getOrderItems();
            for (OrderItem orderItem : orderItems) {
                this.orderItemRepository.deleteById(orderItem.getOrder_item_id());
            }
        }
        this.orderRepository.deleteById(id);
    }

        public void updateOrder(Order order) {
        Optional<Order> orderOptional = this.fetchOrderById(order.getOrder_id());
        if (orderOptional.isPresent()) {
            Order currentOrder = orderOptional.get();
            currentOrder.setStatus(order.getStatus());
            this.orderRepository.save(currentOrder);
        }
    }

    public List<Order> fetchOrderByUser(User user) {
        return this.orderRepository.findByUser(user);
    }

    public List<Order> getOrdersByProduct(Product product) {
        return orderRepository.findByOrderItems_Product(product);
    }
}
