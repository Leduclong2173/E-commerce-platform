package com.duclong.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.duclong.ecommerce.domain.Product;
import com.duclong.ecommerce.domain.Shop;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product save(Product product);

    List<Product> findAll();

    Product findById(long id);
    
    void deleteById(long id);

    List<Product> findAllByShop(Shop shop);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:keyword% OR p.id = :id")
    List<Product> searchByNameOrId(@Param("keyword") String keyword, @Param("id") Long id);

    List<Product> findByNameContainingIgnoreCase(String name);
}
