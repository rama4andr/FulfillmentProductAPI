package com.github.rama4andr.fulfillmentproductapi.dto;

public record ProductDto(String productId, String status, String fulfillmentCenter, Integer quantity, Double value) {
}
