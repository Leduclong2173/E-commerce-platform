package com.duclong.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.duclong.ecommerce.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    User save(User user);

    void deleteById(long id);
    
    List<User> findOneByEmail(String email);

    List<User> findAll();

    User findById(long id);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    User findByEmail(String email);

    User findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.username LIKE %:keyword% OR u.id = :id")
    List<User> searchByUsernameOrId(@Param("keyword") String keyword, @Param("id") Long id);

    List<User> findByNameContainingIgnoreCaseOrProductsNameContainingIgnoreCase(String userKeyword, String productKeyword);
}
