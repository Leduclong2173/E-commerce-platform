package com.duclong.ecommerce.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.duclong.ecommerce.domain.Product;
import com.duclong.ecommerce.domain.Shop;
import com.duclong.ecommerce.domain.User;
import com.duclong.ecommerce.repository.ProductRepository;

@Service
public class ProductServices {

    private final ProductRepository productRepository;
    public ProductServices(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public Product handleSaveProduct(Product product){
        return this.productRepository.save(product);
    }

    public List<Product> getAllProduct(){
        return this.productRepository.findAll();
    }

    public Product getProductById(long id){
        return this.productRepository.findById(id);
    }

    public void deleteproductById(long id){
        this.productRepository.deleteById(id);
    }

    public List<Product> searchByNameOrId(String keyword) {
        if (keyword == null){
            return getAllProduct();
        }
        Long id = null;
        try {
            id = Long.parseLong(keyword);
        } catch (NumberFormatException e) {
        }
        return this.productRepository.searchByNameOrId(keyword, id);
    }

    public List<Product> getAllProductByShop(Shop shop){
        return this.getAllProductByShop(shop);
    }

    public List<Product> searchProducts(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return productRepository.findByNameContainingIgnoreCase(keyword);
    }

}
