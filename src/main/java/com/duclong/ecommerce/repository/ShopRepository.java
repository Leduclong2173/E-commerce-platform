package com.duclong.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.duclong.ecommerce.domain.Shop;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    
    boolean existsByName(String name);

    Shop save(Shop shop);

    Shop findByName(String name);

    Optional<Shop> findById(Long shop_id);

    List<Shop> findAll();

    List<Shop> findByNameContainingIgnoreCase(String name);

    @Query("SELECT s FROM Shop s WHERE s.name LIKE %:keyword% OR s.id = :id")
    List<Shop> searchByNameOrId(@Param("keyword") String keyword, @Param("id") Long id);
}
