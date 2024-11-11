package com.github.rama4andr.fulfillmentproductapi;

import com.github.rama4andr.fulfillmentproductapi.controller.ProductController;
import com.github.rama4andr.fulfillmentproductapi.dto.ProductDto;
import com.github.rama4andr.fulfillmentproductapi.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    public void testGetAllProducts() throws Exception {
        List<ProductDto> products = List.of(new ProductDto("123", "active", "FC1", 10, 100.0));
        given(productService.getAll()).willReturn(products);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].productId").value("123"));
    }

    @Test
    public void testGetProductById() throws Exception {
        ProductDto product = new ProductDto("123", "active", "FC1", 10, 100.0);
        given(productService.getById(1L)).willReturn(product);

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value("123"));
    }

    @Test
    public void testCreateProduct() throws Exception {
        ProductDto createdProduct = new ProductDto("123", "active", "FC1", 10, 100.0);
        given(productService.create(any(ProductDto.class))).willReturn(createdProduct);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"productId\": \"123\", \"status\": \"active\", \"fulfillmentCenter\": \"FC1\", \"quantity\": 10, \"value\": 100.0 }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productId").value("123"));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        ProductDto updatedProduct = new ProductDto("123", "inactive", "FC2", 20, 200.0);
        given(productService.update(eq(1L), any(ProductDto.class))).willReturn(updatedProduct);

        mockMvc.perform(put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"productId\": \"123\", \"status\": \"inactive\", \"fulfillmentCenter\": \"FC2\", \"quantity\": 20, \"value\": 200.0 }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("inactive"));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetProductsByStatus() throws Exception {
        List<ProductDto> products = List.of(new ProductDto("123", "active", "FC1", 10, 100.0));
        given(productService.getProductsByStatus("active")).willReturn(products);

        mockMvc.perform(get("/products/status/active"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].productId").value("123"));
    }

    @Test
    public void testGetTotalValueForSellableProducts() throws Exception {
        given(productService.getTotalValueForSellableProducts("Sellable")).willReturn(100.0);

        mockMvc.perform(get("/products/total-value?status=Sellable"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());

        mockMvc.perform(get("/products/total-value?status=NonExistentStatus"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetTotalValueForInvalidStatus() throws Exception {
        mockMvc.perform(get("/products/total-value?status=NonExistentStatus"))
                .andExpect(status().isNotFound());
    }
}