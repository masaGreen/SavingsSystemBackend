package com.masaGreen.presta.controllers;

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


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.masaGreen.presta.dtos.userRequestsdto.CustomerReqDto;
import com.masaGreen.presta.dtos.userRequestsdto.CustomerUpdateDto;
import com.masaGreen.presta.models.Customer;
import com.masaGreen.presta.services.CustomerService;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    private Customer customer;
    @MockBean
    private CustomerService customerService;
    @Autowired
    private MockMvc mockMvc;
    @BeforeEach
    void setUp(){
        
         customer = Customer.builder()
            .customerId(1L)
            .firstName("John")
            .lastName("Doe")
            .idNumber("12431555")
            .phoneNumber("0712345678")
            .email("johndoe@gmail.com")
            .memberNumber(1L)
            
        .build();
    }
    @Test
    void testGetAllCustomers() throws Exception {

        Mockito.when(customerService.getAllCustomers()).thenReturn(List.of(customer));

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/customer")
                                            .contentType(MediaType.APPLICATION_JSON)                                
                        )
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.size()").value(1));

    }

    @Test
    void testSaveCustomer() throws Exception {
        CustomerReqDto customerReqDto = CustomerReqDto.builder()
                                                    .firstName("John")
                                                    .lastName("Doe")
                                                    .idNumber("12431555")
                                                    .phoneNumber("0712345678")
                                                    .email("johndoe@gmail.com")
                                                    .memberNumber(1L)
                                                  .build();

        // Mockito.when(customerService.saveCustomer(customer)).thenReturn(customer);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/customer")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(customerReqDto)))
        .andExpect(status().isBadRequest());
               
        
      }

      @Test
      void testfindCustomerByMemberNumber() throws Exception{
        Mockito.when(customerService.findByMemberNumber(1L)).thenReturn(Optional.of(customer));

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/customer/findByMemberNumber/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.memberNumber").value(1L));
      }
      @Test
      void testUpdateCustomer() throws JsonProcessingException, Exception{
        CustomerUpdateDto customerUpdated = CustomerUpdateDto.builder()
            
            .phoneNumber("0712345678")
            .email("johndin@gmail.com")
            .memberNumber(1L)
            
        .build();
        Customer customer1 = Customer.builder()
            .customerId(1L)
            .firstName("John")
            .lastName("Doe")
            .idNumber("12431555")
            .phoneNumber("0712345678")
            .email("johndin@gmail.com")
            .memberNumber(1L)
            
        .build();
        Mockito.when(customerService.saveCustomer(customer1)).thenReturn(customer1);

        
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/customer/updateCustomer")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(customerUpdated)))
        .andExpect(status().isBadRequest());

      }

      @Test
      void testDeleteCustomer() throws Exception{
    

        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/customer/deleteCustomer/1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

        
      }
}
