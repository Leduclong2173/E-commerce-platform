package com.duclong.ecommerce.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.duclong.ecommerce.domain.Product;
import com.duclong.ecommerce.domain.User;
import com.duclong.ecommerce.repository.ProductRepository;

@Service
public class ProductServices {

    private final ProductRepository productRepository;
    public ProductServices(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<Product> getAllProduct(User user){
        return this.productRepository.findAllByUser(user);
    }

    public Product handleSaveProduct(Product product){
        return this.productRepository.save(product);
    }

    public Product getProductById(long id){
        return this.productRepository.findById(id);
    }

    public void deleteproductById(long id){
        this.productRepository.deleteById(id);
    }

    public List<Product> getAllProductByUser(User user){
        return this.productRepository.findAllByUser(user);
    }

    public List<Product> searchByNameOrId(String keyword) {
        if (keyword == null) {
            return productRepository.findAll();
        }
        Long id = null;
        try {
            id = Long.parseLong(keyword);
        } catch (NumberFormatException e) {
            // Ignore if keyword cannot be parsed to Long
        }
        return productRepository.searchByNameOrId(keyword, id);
    }

    public List<Product> getRelatedProducts(String keyword) {
        return productRepository.findTop4ByNameNotContainingIgnoreCase(keyword);
    }
}
