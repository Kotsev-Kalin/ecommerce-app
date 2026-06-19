package com.ecommerce.service;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.entity.Category;
import com.ecommerce.entity.Product;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<ProductDto> getAllProducts(String search) {
        List<Product> products = (search == null || search.isBlank())
                ? productRepository.findAll()
                : productRepository.findByNameContainingIgnoreCase(search);

        return products.stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public ProductDto getProductById(Long id) {
        return toDto(findProduct(id));
    }

    @Transactional
    public ProductDto createProduct(ProductDto dto) {
        Product product = Product.builder().build();
        apply(product, dto);
        return toDto(productRepository.save(product));
    }

    @Transactional
    public ProductDto updateProduct(Long id, ProductDto dto) {
        Product product = findProduct(id);
        apply(product, dto);
        return toDto(productRepository.save(product));
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = findProduct(id);
        productRepository.delete(product);
    }

    @Transactional
    public ProductDto updateImage(Long id, String imageUrl) {
        Product product = findProduct(id);
        product.setImageUrl(imageUrl);
        return toDto(productRepository.save(product));
    }

    private Product findProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    private void apply(Product product, ProductDto dto) {
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setImageUrl(dto.getImageUrl());
        product.setStockQuantity(dto.getStockQuantity());
        product.setActive(dto.isActive());

        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            product.setCategory(category);
        } else if (dto.getCategoryName() != null && !dto.getCategoryName().isBlank()) {
            Category category = categoryRepository.findByNameIgnoreCase(dto.getCategoryName())
                    .orElseGet(() -> categoryRepository.save(Category.builder()
                            .name(dto.getCategoryName())
                            .description(dto.getCategoryName() + " products")
                            .build()));
            product.setCategory(category);
        }
    }

    private ProductDto toDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .stockQuantity(product.getStockQuantity())
                .active(product.isActive())
                .categoryId(product.getCategory() != null ? product.getCategory().getId() : null)
                .categoryName(product.getCategory() != null ? product.getCategory().getName() : null)
                .build();
    }
}
