package com.github.rama4andr.fulfillmentproductapi.controller;

import com.github.rama4andr.fulfillmentproductapi.dto.ProductDto;
import com.github.rama4andr.fulfillmentproductapi.exception.ProductNotFoundException;
import com.github.rama4andr.fulfillmentproductapi.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping()
    @Operation(summary = "Get all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products received successfully"),
            @ApiResponse(responseCode = "404", description = "Products not found")
    })
    public ResponseEntity<List<ProductDto>> getAll() {
        List<ProductDto> all = productService.getAll();

        if (all.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(all);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product received successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductDto> getById(@PathVariable Long id) {
        ProductDto byId = productService.getById(id);

        if (byId == null) {
            throw new ProductNotFoundException(id);
        }
        return ResponseEntity.status(HttpStatus.OK).body(byId);
    }

    @PostMapping()
    @Operation(summary = "Create new product")
    @ApiResponse(responseCode = "201", description = "Product successfully created")
    public ResponseEntity<ProductDto> create(@Valid @RequestBody ProductDto productDto) {
        ProductDto created = productService.create(productDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product by ID")
    @ApiResponse(responseCode = "200", description = "Product successfully updated")
    public ResponseEntity<ProductDto> update(@PathVariable Long id,
                                             @RequestBody ProductDto productDto) {
        ProductDto updated = productService.update(id, productDto);

        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product by ID")
    @ApiResponse(responseCode = "204", description = "Product successfully deleted")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        productService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get product by status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products received successfully"),
            @ApiResponse(responseCode = "404", description = "Products not found")
    })
    public ResponseEntity<List<ProductDto>> getByStatus(@PathVariable String status) {
        List<ProductDto> allByStatus = productService.getProductsByStatus(status);

        if (allByStatus.isEmpty()) {
            throw new ProductNotFoundException();
        }
        return ResponseEntity.status(HttpStatus.OK).body(allByStatus);
    }

    @GetMapping("/total-value/{status}")
    @Operation(summary = "Get total value by status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Total value received successfully"),
            @ApiResponse(responseCode = "404", description = "Products not found with current status")
    })
    public ResponseEntity<Double> getTotalValue(@PathVariable String status) {
        Double totalValueForSellableProducts = productService.getTotalValueForSellableProducts(status);

        if (totalValueForSellableProducts == null) {
            throw new ProductNotFoundException();
        }
        return ResponseEntity.status(HttpStatus.OK).body(totalValueForSellableProducts);
    }
}
