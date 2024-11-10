package com.github.rama4andr.fulfillmentproductapi.repository;

import com.github.rama4andr.fulfillmentproductapi.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductEntityRepository extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findAllByDeletedFalse();

    List<ProductEntity> findByStatus(String status);

    List<ProductEntity> findByFulfillmentCenter(String fulfillmentCenter);
}
