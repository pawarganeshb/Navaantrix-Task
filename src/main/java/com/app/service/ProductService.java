package com.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.app.entity.Product;
import com.app.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Create or Save a product (including sub-products)
    public Product createProductWithSubProducts(Product product) {
        return productRepository.save(product);
    }

    // Retrieve a product by ID
    public Product getProduct(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        return productOptional.orElse(null); // Return null if product not found
    }

    // Retrieve a paginated list of products
    public Page<Product> getPaginatedProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }

    // Update an existing product
    public Product updateProduct(Long id, Product productDetails) {
        Optional<Product> existingProductOptional = productRepository.findById(id);
        if (existingProductOptional.isPresent()) {
            Product existingProduct = existingProductOptional.get();
            existingProduct.setName(productDetails.getName());
            return productRepository.save(existingProduct);
        }
        return null; // Return null if product not found
    }

    // Delete a product by ID
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
