package com.masaGreen.presta.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masaGreen.presta.dtos.responseDtos.CollectionResDto;
import com.masaGreen.presta.dtos.responseDtos.ResponseMessageDto;
import com.masaGreen.presta.dtos.userRequestsdto.SavingsProductReqDto;
import com.masaGreen.presta.models.SavingsProduct;
import com.masaGreen.presta.services.SavingsProductsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/savingsproducts")
@Slf4j
@Tag(name="Savings-Products", description="Manages Savings Products")
public class SavingsProductsController {
    @Autowired
    private SavingsProductsService savingsProductsService;

    @PostMapping
     @Operation(
        summary = "creates a savings_products"
        
    )
    
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successfully creted"), 
        @ApiResponse(responseCode = "400", description = "Savings_product already exists"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> createSavingsProduct(@RequestBody @Valid SavingsProductReqDto savingsProductReqDto){
        SavingsProduct savingsProduct = SavingsProduct.builder()
                                                .savingsType(savingsProductReqDto.getSavingsType())
                                                .date(new Date())
                                                .build();

        try {
            SavingsProduct savedProduct = savingsProductsService.saveProduct(savingsProduct);
            if(savedProduct != null){
                return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
            }else{
                return new ResponseEntity<>(ResponseMessageDto.builder().message("savingsProduct already Exists").build(), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(ResponseMessageDto.builder().message("Internal server error, try later").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    @Operation(
        summary = "retrieves all existing Saved_products"
        
    )
    
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrived"), 
   
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    private ResponseEntity<?> getAllSavingsProducts(){
        try {
            List<SavingsProduct> products = savingsProductsService.findAllSavingsProducts();
          
            return new ResponseEntity<>(CollectionResDto.builder().data(products).build(), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(ResponseMessageDto.builder().message("Internal server error, try later").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/deleteSavingProduct/{savingsType}")
    @Operation(
        summary = "uses the savingsProducts type to delete the savingsAccount"
        
    )
    
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully deleted"), 
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    private ResponseEntity<?> deleteSavingsProduct(@PathVariable String savingsType){
        try {
            savingsProductsService.deleteBySavingsType(savingsType);
            return new ResponseEntity<>(ResponseMessageDto.builder().message("deleted successfully").build(), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(ResponseMessageDto.builder().message("Internal server error, try later").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

