package com.github.rama4andr.fulfillmentproductapi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProductDto(

        @NotNull
        @Size(max = 50)
        String productId,

        @NotNull
        String status,

        @NotNull
        String fulfillmentCenter,

        @NotNull
        Integer quantity,

        @NotNull
        Double value
) {}
