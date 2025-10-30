package com.shopzy.product_service.controller;

import com.shopzy.product_service.converter.ProductConverter;
import com.shopzy.product_service.dto.ProductDTO;
import com.shopzy.product_service.entity.Product;
import com.shopzy.product_service.service.internal.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController
{
    @Autowired
    private ProductConverter productConverter;

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> list()
    {
        List<Product> entities = productService.getAllProducts();
        List<ProductDTO> dtos = entities.stream().map(entity -> productConverter.toDto(entity))
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> get(
            @PathVariable Long id)
    {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(productConverter.toDto(product));
    }

    @PostMapping()
    public ResponseEntity<ProductDTO> create(
            @RequestBody @NonNull ProductDTO request
    )
    {
        Product product = productService.addOrUpdate(productConverter.toEntity(request));
        return ResponseEntity.ok(productConverter.toDto(product));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductDTO> create(
            @RequestBody @NonNull ProductDTO request,
            @PathVariable Long id
    )
    {
        request.setId(id); // im case already not done
        Product product = productService.addOrUpdate(productConverter.toEntity(request));
        return ResponseEntity.ok(productConverter.toDto(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(
            @PathVariable Long id
    )
    {
        return ResponseEntity.ok(productService.deleteProduct(id));
    }
}
