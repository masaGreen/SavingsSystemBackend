package com.masaGreen.presta.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.masaGreen.presta.dtos.customer.CreateCustomer;
import com.masaGreen.presta.dtos.customer.CustomerDTO;
import com.masaGreen.presta.dtos.customer.CustomerUpdateDto;
import com.masaGreen.presta.services.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v1/customer")
@Tag(name = "Customers", description = "Endpoints for managing customers")
public class CustomerController {
   
    @Autowired
    private CustomerService customerService;
    
    @PostMapping
    @Operation(
        summary = "saves a customer to database"
        
    )
    
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successfully saved"), 
        @ApiResponse(responseCode = "400", description = "Member number already registered"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
        
    public ResponseEntity<String> saveCustomer(@RequestBody @Valid CreateCustomer createCustomer){
      
        return new ResponseEntity<>(customerService.saveCustomer(createCustomer), HttpStatus.CREATED);
        
    }


    @GetMapping
    @Operation(
        summary = "Fetches all customers from database"
        
    )
    
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully fetched"), 
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<CustomerDTO>> getAllCustomers(){
       return new ResponseEntity<>(customerService.getAllCustomers(), HttpStatus.OK);
    }


    @GetMapping("/findByMemberNumber/{memberNumber}")
     @Operation(
        summary = "returns a Customer with the provided membeNumber"
        
    )
    
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully saved"), 
        @ApiResponse(responseCode = "404", description = "Nt found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    private ResponseEntity<CustomerDTO> findCustomerByIdNumber(@PathVariable String idNumber){
        return new ResponseEntity<>(customerService.findByIdNumber(idNumber), HttpStatus.OK);
    }

   
     @Operation(
        summary = "Updates a customer's phone or email address"
        
    )
    
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully updated"), 
        @ApiResponse(responseCode = "400", description = "Member number is incorrect"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
     @PutMapping("/updateCustomer/{idNumber}")
    private ResponseEntity<String> updateCustomer(@RequestBody @Valid CustomerUpdateDto customerUpdateDto, @PathVariable String idNumber ){
        
        return new ResponseEntity<>(customerService.updateCustomerByIdNumber(idNumber, customerUpdateDto), HttpStatus.OK);
    }

    
     @Operation(
        summary = "uses memberNumber to delete a customer from database"
        
    )
    
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully deleted"), 
        
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/deleteCustomer/{id}")
    private ResponseEntity<String> deleteCustomer(@PathVariable String id){
        return new ResponseEntity<>(customerService.deleteAccountById(id), HttpStatus.OK);
    }

}
