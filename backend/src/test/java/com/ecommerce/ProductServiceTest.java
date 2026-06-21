package com.ecommerce;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.entity.Category;
import com.ecommerce.entity.Product;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.ProductService;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    private ProductDto productDto;

    @BeforeEach
    void setUp() {
        productDto = ProductDto.builder()
                .name("Laptop")
                .description("High-end laptop")
                .price(new BigDecimal("1499.99"))
                .stockQuantity(5)
                .categoryName("Laptops")
                .active(true)
                .build();
    }

    @Test
    void shouldCreateProductWhenCategoryExists() {
        Category category = Category.builder().id(2L).name("Laptops").build();
        Product saved = Product.builder()
                .id(1L)
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .stockQuantity(productDto.getStockQuantity())
                .category(category)
                .active(true)
                .build();

        when(categoryRepository.findByNameIgnoreCase("Laptops")).thenReturn(Optional.of(category));
        when(productRepository.save(org.mockito.ArgumentMatchers.any(Product.class))).thenReturn(saved);

        ProductDto result = productService.createProduct(productDto);

        assertNotNull(result.getId());
        assertEquals("Laptop", result.getName());
        assertEquals("Laptops", result.getCategoryName());
        verify(productRepository).save(org.mockito.ArgumentMatchers.any(Product.class));
    }

    @Test
    void shouldGetProductById() {
        Category category = Category.builder().id(2L).name("Laptops").build();
        Product product = Product.builder()
                .id(1L)
                .name("Laptop")
                .description("High-end laptop")
                .price(new BigDecimal("1499.99"))
                .stockQuantity(5)
                .category(category)
                .active(true)
                .build();

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductDto result = productService.getProductById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Laptop", result.getName());
        assertEquals("Laptops", result.getCategoryName());
    }

    @Test
    void shouldThrowWhenProductNotFound() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(99L));
    }
}
