package com.duclong.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.duclong.ecommerce.domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
    
    Category save(Category category);

    List<Category> findAll();

    Boolean existsByName(String name);

    void deleteById(long id);

    Category findById(long id);

    Category findByName(String name);

    long count();

    @Query("SELECT c FROM Category c WHERE c.name LIKE %:keyword% OR c.id = :id")
    List<Category> searchByNameOrId(@Param("keyword") String keyword, @Param("id") Long id);
}
