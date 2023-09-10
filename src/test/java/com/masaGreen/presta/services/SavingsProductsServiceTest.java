package com.masaGreen.presta.services;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.masaGreen.presta.models.SavingsProduct;
import com.masaGreen.presta.repositories.SavingsProductsRepository;

@SpringBootTest
public class SavingsProductsServiceTest {
    private long id;
    private SavingsProduct savingsProduct;

    @MockBean
    private SavingsProductsRepository savingsProductsRepository;
    @Autowired
    private SavingsProductsService savingsProductsService;

    @BeforeEach
    void setUp(){
        id =1;
             
        savingsProduct = SavingsProduct.builder()
                            .productId(id)
                            .savingsType("Vacation")
                            .date(new Date()) 
                            .balance(BigDecimal.ZERO)           
                        .build();
    }

    @Test
    void testDeleteBySavingsType() {
        Mockito.when(savingsProductsRepository.save(savingsProduct)).thenReturn(savingsProduct);
        savingsProductsService.deleteBySavingsType("Vacation");
        assertTrue(savingsProductsService.findAllSavingsProducts().size() == 0);

    }

    @Test
    void testFindAllSavingsProducts() {
         Mockito.when(savingsProductsRepository.findAll()).thenReturn(List.of(savingsProduct));
       
        assertTrue(savingsProductsService.findAllSavingsProducts().size() == 1);
        assertEquals(savingsProductsService.findAllSavingsProducts().get(0).getProductId(), 1);

    }

    @Test
    void testFindBySavingsType() {
        Mockito.when(savingsProductsRepository.findBySavingsType("Vacation")).thenReturn(Optional.of(savingsProduct));
       assertTrue(savingsProductsService.findBySavingsType("Vacation").isPresent());
    }

    @Test
    void testSaveProduct() {
        Mockito.when(savingsProductsRepository.save(savingsProduct)).thenReturn(savingsProduct);
        assertEquals(savingsProductsService.saveProduct(savingsProduct).getProductId(), 1);
    }
}
