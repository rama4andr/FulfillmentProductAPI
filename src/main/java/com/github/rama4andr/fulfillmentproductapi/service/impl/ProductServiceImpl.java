package com.github.rama4andr.fulfillmentproductapi.service.impl;

import com.github.rama4andr.fulfillmentproductapi.dto.ProductDto;
import com.github.rama4andr.fulfillmentproductapi.entity.ProductEntity;
import com.github.rama4andr.fulfillmentproductapi.exception.ProductNotFoundException;
import com.github.rama4andr.fulfillmentproductapi.repository.ProductEntityRepository;
import com.github.rama4andr.fulfillmentproductapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductEntityRepository productEntityRepository;

    @Override
    public List<ProductDto> getAll() {
        List<ProductEntity> all = productEntityRepository.findAllByDeletedFalse();

        return all.stream()
                .map(this::entityToDto)
                .toList();
    }

    @Override
    public ProductDto getById(Long id) {
        ProductEntity productEntity = getProductEntity(id);

        return entityToDto(productEntity);
    }

    @Override
    public ProductDto create(ProductDto productDto) {

        ProductEntity savedEntity = productEntityRepository.save(dtoToEntity(productDto));
        return entityToDto(savedEntity);
    }

    @Override
    public ProductDto update(Long id, ProductDto productDto) {
        ProductEntity productEntity = getProductEntity(id);

        if (productDto.productId() != null) {
            productEntity.setProductId(productDto.productId());
        }

        if (productDto.status() != null) {
            productEntity.setStatus(productDto.status());
        }

        if (productDto.fulfillmentCenter() != null) {
            productEntity.setFulfillmentCenter(productDto.fulfillmentCenter());
        }

        if (productDto.quantity() != null) {
            productEntity.setQuantity(productDto.quantity());
        }

        if (productDto.value() != null) {
            productEntity.setValue(productDto.value());
        }

        ProductEntity updatedEntity = productEntityRepository.save(productEntity);
        return entityToDto(updatedEntity);
    }

    @Override
    public void delete(Long id) {
        ProductEntity productEntity = getProductEntity(id);

        productEntity.setDeleted(true);
        productEntityRepository.save(productEntity);
    }

    @Override
    public List<ProductDto> getProductsByStatus(String status) {
        List<ProductEntity> byStatus = productEntityRepository.findByStatus(status);

        return byStatus.stream()
                .map(this::entityToDto)
                .toList();
    }

    @Override
    public Double getTotalValueForSellableProducts(String status) {
        List<ProductEntity> byStatus = productEntityRepository.findByStatus(status);

        return byStatus.stream()
                .mapToDouble(ProductEntity::getValue)
                .sum();
    }

    private ProductEntity getProductEntity(Long id) {
        return productEntityRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    private ProductDto entityToDto(ProductEntity productEntity) {
        return new ProductDto(
                productEntity.getProductId(),
                productEntity.getStatus(),
                productEntity.getFulfillmentCenter(),
                productEntity.getQuantity(),
                productEntity.getValue());
    }

    private ProductEntity dtoToEntity(ProductDto productDto) {
        return ProductEntity.builder()
                .productId(productDto.productId())
                .status(productDto.status())
                .fulfillmentCenter(productDto.fulfillmentCenter())
                .quantity(productDto.quantity())
                .value(productDto.value())
                .build();
    }
}
