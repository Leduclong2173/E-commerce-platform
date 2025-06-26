package com.duclong.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.duclong.ecommerce.domain.Product;
import com.duclong.ecommerce.domain.User;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product save(Product product);

    List<Product> findAll();

    Product findById(long id);
    
    void deleteById(long id);

    List<Product> findAllByUser(User user);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:keyword% OR p.product_id = :id")
    List<Product> searchByNameOrId(@Param("keyword") String keyword, @Param("id") Long id);

    @Query("SELECT p FROM Product p WHERE p.name NOT LIKE %:keyword% AND p.shortDesc NOT LIKE %:keyword% ORDER BY p.product_id DESC")
    List<Product> findTop4ByNameNotContainingIgnoreCase(@Param("keyword") String keyword);
}
