package com.github.rama4andr.fulfillmentproductapi.exception;

public class ProductNotFoundException extends RuntimeException {

    private static final String PRODUCTS_NOT_FOUND = "Products not found";
    private static final String PRODUCT_NOT_FOUND_BY_ID = "No product found with id: ";

    public ProductNotFoundException() {
        super(PRODUCTS_NOT_FOUND);
    }

    public ProductNotFoundException(Long id) {
        super(PRODUCT_NOT_FOUND_BY_ID + id);
    }

    public ProductNotFoundException(String message) {
        super(message);
    }
}
