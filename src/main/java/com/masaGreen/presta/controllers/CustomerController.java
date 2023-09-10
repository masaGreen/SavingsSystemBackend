package com.masaGreen.presta.controllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;



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

import com.masaGreen.presta.dtos.responseDtos.CollectionResDto;
import com.masaGreen.presta.dtos.responseDtos.ResponseMessageDto;
import com.masaGreen.presta.dtos.userRequestsdto.CustomerReqDto;
import com.masaGreen.presta.dtos.userRequestsdto.CustomerUpdateDto;
import com.masaGreen.presta.models.Customer;
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
        
    public ResponseEntity<?> saveCustomer(@RequestBody @Valid CustomerReqDto customerReqDto){
        Customer customer = Customer.builder()
                                .firstName(customerReqDto.getFirstName())
                                .lastName(customerReqDto.getLastName())
                                .idNumber(customerReqDto.getIdNumber())
                                .phoneNumber(customerReqDto.getPhoneNumber())
                                .email(customerReqDto.getEmail())
                                .memberNumber(customerReqDto.getMemberNumber())
                                .dateOfJoin(new Date())
                                .build();
        try {
            Customer savedCustomer = customerService.saveCustomer(customer);
            if(savedCustomer != null){
                return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
            }else{
                return new ResponseEntity<>(ResponseMessageDto.builder().message("customer with the member number already exists").build(),HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
           log.error(e.getMessage(), e);
           return new ResponseEntity<>(ResponseMessageDto.builder().message("Internal server error, try later").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }


    @GetMapping
    @Operation(
        summary = "Fetches all customers from database"
        
    )
    
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully fetched"), 
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> getAllCustomers(){
        try {
            List<Customer> customers = customerService.getAllCustomers();
            return new ResponseEntity<>(CollectionResDto.builder().data(customers).build(), HttpStatus.OK) ;
        } catch (Exception e) {
           log.error(e.getMessage(), e);
           return new ResponseEntity<>(ResponseMessageDto.builder().message("Internal server error, try later").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
    private ResponseEntity<?> findCustomerByMemberNumber(@PathVariable String memberNumber){
        try {
            Optional<Customer> foundCustomer = customerService.findByMemberNumber(Long.parseLong(memberNumber));
            if(foundCustomer.isPresent()){
                return new ResponseEntity<>(foundCustomer, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(ResponseMessageDto.builder().message("Customer with the member number doesn't exist").build(), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
           log.error(e.getMessage(), e);
           return new ResponseEntity<>(ResponseMessageDto.builder().message("Internal server error, try later").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateCustomer")
     @Operation(
        summary = "Updates a customer's phone or email address"
        
    )
    
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully updated"), 
        @ApiResponse(responseCode = "400", description = "Member number is incorrect"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    private ResponseEntity<?> updateCustomer(@RequestBody @Valid CustomerUpdateDto customerUpdateDto){
        try {
          
            Optional<Customer> foundCustomer = customerService.findByMemberNumber(customerUpdateDto.getMemberNumber());
            if(foundCustomer.isPresent()){
                Customer updatedCustomer = foundCustomer.get();
                updatedCustomer.setEmail(customerUpdateDto.getEmail());
                updatedCustomer.setPhoneNumber(customerUpdateDto.getPhoneNumber());
                System.out.println(customerService.saveCustomer(updatedCustomer));                   
                return new ResponseEntity<>(customerService.saveCustomer(updatedCustomer), HttpStatus.CREATED);
            }else{
                return new ResponseEntity<>(ResponseMessageDto.builder().message("customer with the provided memeber doesn't exists").build(),HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(ResponseMessageDto.builder().message("Internal server error, try later").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteCustomer/{memberNumber}")
     @Operation(
        summary = "uses memberNumber to delete a customer from database"
        
    )
    
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully deleted"), 
        
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    private ResponseEntity<?> deleteCustomer(@PathVariable String memberNumber){
        try {
            
            customerService.deleteByMemberNumber(Long.parseLong(memberNumber));
            return new ResponseEntity<>(ResponseMessageDto.builder().message("deleted successfully").build(), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(ResponseMessageDto.builder().message("Internal server error, try later").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
