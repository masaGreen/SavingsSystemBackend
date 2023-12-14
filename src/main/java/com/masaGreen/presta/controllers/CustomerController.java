package com.masaGreen.presta.controllers;

import com.masaGreen.presta.dtos.customer.CreateCustomer;
import com.masaGreen.presta.dtos.customer.CustomerDTO;
import com.masaGreen.presta.dtos.customer.CustomerUpdateDto;
import com.masaGreen.presta.services.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/customer")
@Tag(name = "Customers", description = "Endpoints for managing customers")
public class CustomerController {
   
    
    private final CustomerService customerService;

    @Operation(summary = "register a new customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "customer registered successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "409", description = "customer already exists",
                    content = @Content(examples = @ExampleObject(value = "{'message': 'customer already exists'}"))),

    })
    @PostMapping("/create")
    public ResponseEntity<String> saveCustomer(@RequestBody @Valid CreateCustomer createCustomer){
      
        return new ResponseEntity<>(customerService.saveCustomer(createCustomer), HttpStatus.CREATED);
        
    }


    @Operation(summary = "fetch all customers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "customers fetched successfully",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema( schema = @Schema(implementation = CustomerDTO.class)))})
    })
    @GetMapping("/all-customers")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers(){
       return new ResponseEntity<>(customerService.getAllCustomers(), HttpStatus.OK);
    }



    @Operation(summary = "fetch customer by id-number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "customer fetched successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerDTO.class))}),
            @ApiResponse(responseCode = "404", description = "customer not found",
                    content = @Content(examples = @ExampleObject(value = "{'message': 'customer not found'}"))),

    })
    @GetMapping("/findByIdNumber/{idNumber}")
    private ResponseEntity<CustomerDTO> findCustomerByIdNumber(@PathVariable String idNumber){
        return new ResponseEntity<>(customerService.findByIdNumber(idNumber), HttpStatus.OK);
    }


    @Operation(summary = "edit customer details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "customer edited successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "404", description = "customer not found",
                    content = @Content(examples = @ExampleObject(value = "{'message': 'customer not found'}"))),

    })
     @PutMapping("/updateCustomer/{idNumber}")
    private ResponseEntity<String> updateCustomer(@RequestBody @Valid CustomerUpdateDto customerUpdateDto, @PathVariable String idNumber ){
        
        return new ResponseEntity<>(customerService.updateCustomerByIdNumber(idNumber, customerUpdateDto), HttpStatus.OK);
    }

    @Operation(summary = "fetch customer by i")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "customer details and accounts deleted successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "404", description = "customer not found",
                    content = @Content(examples = @ExampleObject(value = "{'message': 'customer not found'}"))),

    })
    @DeleteMapping("/deleteCustomer/{id}")
    private ResponseEntity<String> deleteCustomer(@PathVariable String id){
        return new ResponseEntity<>(customerService.deleteCustomerById(id), HttpStatus.OK);
    }

}
