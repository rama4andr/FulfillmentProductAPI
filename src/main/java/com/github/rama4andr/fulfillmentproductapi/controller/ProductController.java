package com.github.rama4andr.fulfillmentproductapi.controller;

import com.github.rama4andr.fulfillmentproductapi.dto.ProductDto;
import com.github.rama4andr.fulfillmentproductapi.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public ResponseEntity<List<ProductDto>> getAll() {
        List<ProductDto> all = productService.getAll();

        return Optional.ofNullable(all).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable Long id) {
        ProductDto byId = productService.getById(id);

        return Optional.ofNullable(byId).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping()
    public ResponseEntity<ProductDto> create(@Valid @RequestBody ProductDto productDto) {
        ProductDto created = productService.create(productDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> update(@PathVariable Long id,
                                             @RequestBody ProductDto productDto) {
        ProductDto updated = productService.update(id, productDto);

        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        productService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ProductDto>> getByStatus(@PathVariable String status) {
        List<ProductDto> allByStatus = productService.getProductsByStatus(status);

        return Optional.ofNullable(allByStatus).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/total-value/{status}")
    public ResponseEntity<Double> getTotalValue(@PathVariable String status) {
        Double totalValueForSellableProducts = productService.getTotalValueForSellableProducts(status);

        return Optional.ofNullable(totalValueForSellableProducts).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

}
