package com.duclong.ecommerce.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.duclong.ecommerce.domain.Cart;
import com.duclong.ecommerce.domain.CartItem;
import com.duclong.ecommerce.domain.Product;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long>{

    boolean existsByCartAndProduct(Cart cart, Product product);

    CartItem findByCartAndProduct(Cart cart, Product product);
}
