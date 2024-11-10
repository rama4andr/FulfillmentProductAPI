package com.github.rama4andr.fulfillmentproductapi.service;

import com.github.rama4andr.fulfillmentproductapi.dto.ProductDto;

import java.util.List;

public interface ProductService {

    List<ProductDto> getAll();

    ProductDto getById(Long id);

    ProductDto create(ProductDto productDto);

    ProductDto update(Long id, ProductDto productDto);

    void delete(Long id);

    List<ProductDto> getProductsByStatus(String status);

    Double getTotalValueForSellableProducts(String status);
}
