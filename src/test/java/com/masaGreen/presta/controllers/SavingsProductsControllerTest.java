package com.masaGreen.presta.controllers;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.masaGreen.presta.models.SavingsProduct;
import com.masaGreen.presta.services.SavingsProductsService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SavingsProductsController.class)
public class SavingsProductsControllerTest {
    private SavingsProduct savingsProduct;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SavingsProductsService savingsProductsService;

    @BeforeEach
    void setUp(){
          
        savingsProduct = SavingsProduct.builder()
                            .productId(1L)
                            .savingsType("Vacation")
                            .date(new Date()) 
                            .balance(BigDecimal.ZERO)           
                        .build();
        
    }
     @Test
    void testgetAllSavingsProducts() throws Exception{
        Mockito.when(savingsProductsService.findAllSavingsProducts()).thenReturn(List.of(savingsProduct));

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/savingsproducts")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    void testDeleteBySavingsType() throws Exception{
        
          mockMvc.perform(MockMvcRequestBuilders.delete("/v1/savingsproducts/deleteSavingProduct/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
           
    }
  
    @Test
    void testCreateSavingsProduct() throws Exception {
        Mockito.when(savingsProductsService.saveProduct(savingsProduct)).thenReturn(savingsProduct);

         mockMvc.perform(MockMvcRequestBuilders.post("/v1/savingsproducts")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(savingsProduct)))
        .andExpect(status().isBadRequest());

    }
   
}
