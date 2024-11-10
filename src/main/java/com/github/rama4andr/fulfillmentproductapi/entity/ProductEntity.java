package com.github.rama4andr.fulfillmentproductapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id")
    @NotNull
    @Size(max = 50)
    private String productId;

    @Column(name = "status")
    private String status;

    @Column(name = "fulfillment_center")
    private String fulfillmentCenter;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "value")
    private Double value;

    @Column(name = "is_deleted", nullable = false)
    private boolean deleted = false;
}
