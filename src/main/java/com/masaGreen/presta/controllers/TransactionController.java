package com.masaGreen.presta.controllers;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masaGreen.presta.dtos.responseDtos.CollectionResDto;
import com.masaGreen.presta.dtos.responseDtos.ResponseMessageDto;
import com.masaGreen.presta.dtos.responseDtos.SavingsAmountResDto;
import com.masaGreen.presta.dtos.userRequestsdto.TransactionReqDto;
import com.masaGreen.presta.models.SavingsProduct;
import com.masaGreen.presta.models.Transaction;
import com.masaGreen.presta.services.SavingsProductsService;
import com.masaGreen.presta.services.TransactionsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/transactions")
@Slf4j
@Tag(name="Transactions" , description="Manages all transactions")
public class TransactionController {
    @Autowired
    private TransactionsService transactionsService;
    @Autowired
    private SavingsProductsService savingsProductsService;

    @PostMapping("/deposit")
     @Operation(
        summary = "saves a deposit type of transaction"
        
    )
    
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successfully saved"), 
        @ApiResponse(responseCode = "501", description = "Unsuccessful transaction"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    private ResponseEntity<?> createDepositTransaction(@RequestBody @Valid TransactionReqDto transactionReqDto){
        
        
        Optional<SavingsProduct> savingsProduct = savingsProductsService.findBySavingsType(transactionReqDto.getSavingsType());

        Transaction transaction = Transaction.builder()
                                    .memberNumber(transactionReqDto.getMemberNumber())
                                    .methodOfPayment(transactionReqDto.getMethodOfPayment())
                                    .amount(transactionReqDto.getAmount())
                                    .date(new Date())
                                    .savingsProduct(savingsProduct.get())
                                    .build();

        try {
            Transaction savedTransaction = transactionsService.saveDepositTransaction(transaction);
            if(transaction != null){
                return new ResponseEntity<>(savedTransaction, HttpStatus.CREATED);
            }else{
                return new ResponseEntity<>(ResponseMessageDto.builder().message("Transacation unsuccessful").build(), HttpStatus.NOT_IMPLEMENTED);
            }
        } catch (Exception e) {

            log.error(e.getMessage(), e);
            return new ResponseEntity<>(ResponseMessageDto.builder().message("Internal server error, try later").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/withdraw")
    @Operation(
        summary = "saves a withdrawal type of transaction"
        
    )
    
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successfully saved"), 
        @ApiResponse(responseCode = "501", description = "Unsuccessful transaction"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    private ResponseEntity<?> createWithdrawTransaction(@RequestBody @Valid TransactionReqDto transactionReqDto){
        SavingsProduct savingsProduct = SavingsProduct.builder()
                                            .date(new Date())
                                            .savingsType(transactionReqDto.getSavingsType())
                                            .build();

        Transaction transaction = Transaction.builder()
                                    .memberNumber(transactionReqDto.getMemberNumber())
                                    .methodOfPayment(transactionReqDto.getMethodOfPayment())
                                    .amount(transactionReqDto.getAmount())
                                    
                                    .date(new Date())
                                    .savingsProduct(savingsProduct)
                                    .build();

        try {
            Transaction savedTransaction = transactionsService.saveWithdrawTransaction(transaction);
            
            if(savedTransaction != null){
               
                return new ResponseEntity<>(savedTransaction, HttpStatus.CREATED);
            }else{
                return new ResponseEntity<>(ResponseMessageDto.builder().message("Transacation unsuccessful").build(), HttpStatus.NOT_IMPLEMENTED);
            }
        } catch (Exception e) {

            log.error(e.getMessage(), e);
            return new ResponseEntity<>(ResponseMessageDto.builder().message("Internal server error, try later").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/totalSavings/{memberNumber}")
    @Operation(
        summary = "uses memberNumber to track totalSavings for the member"
        
    )
    
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved"), 
         @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    private ResponseEntity<?> getTotalSavingsPerMember(@PathVariable String memberNumber){
        try {
            BigDecimal totalMemberSavings = transactionsService.getSingleMemberTotalSavings(memberNumber);
            return new ResponseEntity<>(SavingsAmountResDto.builder().totalSavings(totalMemberSavings).build(), HttpStatus.OK); 
        } catch (Exception e) {
            
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(ResponseMessageDto.builder().message("Internal server error, try later").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
     @GetMapping("/totalSavings")
     @Operation(
        summary = "tracks total savings by all members"
        
    )
    
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved"), 
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    private ResponseEntity<?> getTotalMemberSavings(){
        try {
             BigDecimal totalMemberSavings = transactionsService.getAllMembersTotalSavings();
            return new ResponseEntity<>(SavingsAmountResDto.builder().totalSavings(totalMemberSavings).build(), HttpStatus.OK); 
        } catch (Exception e) {
            
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(ResponseMessageDto.builder().message("Internal server error, try later").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    @Operation(
        summary = "fetches all transactions"
        
    )
    
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully fetched"), 
   
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    private ResponseEntity<?> getAllTransactions(){
        try {
            List<Transaction> transactions = transactionsService.getAllTransactions();
            return new ResponseEntity<>(CollectionResDto.builder().data(transactions).build(), HttpStatus.OK); 
        } catch (Exception e) {
            
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(ResponseMessageDto.builder().message("Internal server error, try later").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{memberNumber}")
    @Operation(
        summary = "uses memberNumber to fetch all  transactions by the member "
        
    )
    
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successfully fetched"), 
    
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    
    private ResponseEntity<?> getAllTransactionsByMember(@PathVariable String memberNumber){
        try {
            List<Transaction> transactions = transactionsService.getAllTransactionsByMember(memberNumber);
            return new ResponseEntity<>(CollectionResDto.builder().data(transactions).build(), HttpStatus.OK); 
        } catch (Exception e) {
            
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(ResponseMessageDto.builder().message("Internal server error, try later").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
   
    
}
