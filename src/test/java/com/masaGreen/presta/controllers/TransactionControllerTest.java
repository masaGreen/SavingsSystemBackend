package com.masaGreen.presta.controllers;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
import com.masaGreen.presta.dtos.userRequestsdto.TransactionReqDto;
import com.masaGreen.presta.models.SavingsProduct;
import com.masaGreen.presta.models.Transaction;
import com.masaGreen.presta.services.SavingsProductsService;
import com.masaGreen.presta.services.TransactionsService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {
    private Transaction transaction;
    private SavingsProduct savingsProduct;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SavingsProductsService savingsProductsService;
    @MockBean
    private TransactionsService transactionsService;

    @BeforeEach
    void setUp(){
        BigDecimal bd = new BigDecimal(20);
       
       savingsProduct = SavingsProduct.builder()
                            .productId(1L)
                            .savingsType("Vacation")
                            .date(new Date()) 
                            .balance(BigDecimal.ZERO)           
                        .build();
        transaction = Transaction.builder()
                            .transactionId(1L)
                            .memberNumber(1L)
                            .amount(bd)
                            .currentAmount(bd)
                            .date(new Date())
                            .methodOfPayment("Cash")
                            .savingsProduct(savingsProduct)
            .build();
    }

    @Test
    void testGetAllTransactions() throws Exception{
        Mockito.when(transactionsService.getAllTransactions()).thenReturn(List.of(transaction));

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/transactions")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }
    @Test 
    void testGetAllTransactionsByMember() throws Exception{
        Mockito.when(transactionsService.getAllTransactionsByMember("1")).thenReturn(List.of(transaction));

         mockMvc.perform(MockMvcRequestBuilders.get("/v1/transactions")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));

    }
    @Test
    void testSaveWithdrawTransaction() throws Exception{
         TransactionReqDto transactionReqDto = TransactionReqDto.builder()
                                        .memberNumber(1L)
                                        .amount(new BigDecimal(150))
                                        .methodOfPayment("Cash")
                                        .savingsType("Vacation")
                                        .build();
        Mockito.when(transactionsService.saveDepositTransaction(transaction)).thenReturn(transaction);

        when(savingsProductsService.findBySavingsType("Vacation")).thenReturn(Optional.of(savingsProduct));
        when(transactionsService.saveDepositTransaction(transaction)).thenReturn(transaction);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/transactions/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(transactionReqDto)))
                .andExpect(status().isCreated());
                

    }

    @Test
    void testGetTotalMemberSavings() throws Exception{
        Mockito.when(transactionsService.getAllMembersTotalSavings()).thenReturn(new BigDecimal(200));

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/transactions/totalSavings")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalSavings").value(new BigDecimal(200)));
    }
    @Test
    void testSaveDepositTransaction() throws Exception{
        TransactionReqDto transactionReqDto = TransactionReqDto.builder()
                                        .memberNumber(1L)
                                        .amount(new BigDecimal(150))
                                        .methodOfPayment("Cash")
                                        .savingsType("Vacation")
                                        .build();
        Mockito.when(transactionsService.saveDepositTransaction(transaction)).thenReturn(transaction);

        when(savingsProductsService.findBySavingsType("Vacation")).thenReturn(Optional.of(savingsProduct));
        when(transactionsService.saveDepositTransaction(transaction)).thenReturn(transaction);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/transactions/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(transactionReqDto)))
                .andExpect(status().isCreated());
               
                

    }
    @Test
    void testGetTotalSavingsPerMember() throws Exception{
        Mockito.when(transactionsService.getSingleMemberTotalSavings("1")).thenReturn(new BigDecimal(200));

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/transactions/totalSavings/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalSavings").value(new BigDecimal(200)));
    }

}
