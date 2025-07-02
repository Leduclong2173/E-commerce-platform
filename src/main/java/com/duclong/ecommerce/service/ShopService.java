package com.duclong.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.duclong.ecommerce.domain.Product;
import com.duclong.ecommerce.domain.Shop;
import com.duclong.ecommerce.repository.ProductRepository;
import com.duclong.ecommerce.repository.ShopRepository;

@Service
public class ShopService {
    
    private final ShopRepository shopRepository;
    private final ProductRepository productRepository;

    public ShopService(
    ShopRepository shopRepository,
    ProductRepository productRepository){
        this.shopRepository = shopRepository;
        this.productRepository = productRepository;
    }
    public boolean checkNameExist(String name){
        return this.shopRepository.existsByName(name);
    }

    public Shop handleSaveShop(Shop shop){
        return this.shopRepository.save(shop);
    }

    public Shop getShopByName(String name){
        return this.shopRepository.findByName(name);
    }

    public List<Product> getAllProduct(Shop shop){
        return this.productRepository.findAll();
    }

    public List<Shop> getAllShop(){
        return this.shopRepository.findAll();
    }

    public List<Shop> searchShops(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return shopRepository.findByNameContainingIgnoreCase(keyword);
    }

    public List<Shop> searchByNameOrId(String keyword) {
        if (keyword == null){
            return getAllShop();
        }
        Long id = null;
        try {
            id = Long.parseLong(keyword);
        } catch (NumberFormatException e) {
        }
        return this.shopRepository.searchByNameOrId(keyword, id);
    }

    public Optional<Shop> getShopById (Long id){
        return this.shopRepository.findById(id);
    }
}
