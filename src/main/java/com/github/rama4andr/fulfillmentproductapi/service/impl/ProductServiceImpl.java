package com.github.rama4andr.fulfillmentproductapi.service.impl;

import com.github.rama4andr.fulfillmentproductapi.dto.ProductDto;
import com.github.rama4andr.fulfillmentproductapi.entity.ProductEntity;
import com.github.rama4andr.fulfillmentproductapi.repository.ProductEntityRepository;
import com.github.rama4andr.fulfillmentproductapi.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductEntityRepository productEntityRepository;

    public ProductServiceImpl(ProductEntityRepository productEntityRepository) {
        this.productEntityRepository = productEntityRepository;
    }

    @Override
    public List<ProductDto> getAll() {
        List<ProductEntity> all = productEntityRepository.findAllByDeletedFalse();

        if (!all.isEmpty()) {
            return all.stream()
                    .map(this::entityToDto)
                    .toList();
        } else {
            throw new IllegalStateException("No products found");
        }
    }

    @Override
    public ProductDto getById(Long id) {

        ProductEntity productEntity = productEntityRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("No product found with id: " + id));

        return entityToDto(productEntity);
    }

    @Override
    public ProductDto create(ProductDto productDto) {

        ProductEntity savedEntity = productEntityRepository.save(dtoToEntity(productDto));
        return entityToDto(savedEntity);
    }

    @Override
    public ProductDto update(Long id, ProductDto productDto) {
        ProductEntity productEntity = productEntityRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("No product found with id: " + id));

        productEntity.setProductId(productDto.productId());
        productEntity.setStatus(productDto.status());
        productEntity.setFulfillmentCenter(productDto.fulfillmentCenter());
        productEntity.setQuantity(productDto.quantity());
        productEntity.setValue(productDto.value());

        ProductEntity updatedEntity = productEntityRepository.save(productEntity);
        return entityToDto(updatedEntity);
    }

    @Override
    public void delete(Long id) {
        ProductEntity productEntity = productEntityRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("No product found with id: " + id));

        productEntity.setDeleted(true);
        productEntityRepository.save(productEntity);
    }

    @Override
    public List<ProductDto> getProductsByStatus(String status) {
        List<ProductEntity> byStatus = productEntityRepository.findByStatus(status);

        if (!byStatus.isEmpty()) {
            return byStatus.stream()
                    .map(this::entityToDto)
                    .toList();
        } else {
            throw new IllegalStateException("No products found");
        }
    }

    @Override
    public Double getTotalValueForSellableProducts(String status) {
        List<ProductEntity> byStatus = productEntityRepository.findByStatus(status);

        if (!byStatus.isEmpty()) {
            return byStatus.stream()
                    .mapToDouble(ProductEntity::getValue)
                    .sum();
        } else {
            throw new IllegalStateException("No products found");
        }
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
