package com.github.rama4andr.fulfillmentproductapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProductDto(

        @NotNull
        @NotBlank
        @Size(max = 50)
        String productId,

        @NotNull
        @NotBlank
        String status,

        @NotNull
        @NotBlank
        String fulfillmentCenter,

        @NotNull
        Integer quantity,

        @NotNull
        Double value
) {}
