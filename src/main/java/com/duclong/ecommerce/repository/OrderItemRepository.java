package com.duclong.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.duclong.ecommerce.domain.Cart;
import com.duclong.ecommerce.domain.CartItem;
import com.duclong.ecommerce.domain.OrderItem;
import com.duclong.ecommerce.domain.Product;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
