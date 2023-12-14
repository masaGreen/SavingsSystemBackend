package com.masaGreen.presta.controllers;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masaGreen.presta.dtos.account.AccountCreationResDTO;
import com.masaGreen.presta.dtos.account.CreateAccountDTO;
import com.masaGreen.presta.dtos.transactions.BalanceInquiryDTO;
import com.masaGreen.presta.dtos.transactions.BalanceDTO;
import com.masaGreen.presta.services.AccountService;

import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@Tag(name="Accounts" , description="accounts management")
public class AccountController {

    private final AccountService accountService;
    @Operation(summary = "Register a new account for customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account successfully created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountCreationResDTO.class))}),
            @ApiResponse(responseCode = "404", description = "customer must be registered to create account or provide valid account type",
                    content = @Content(examples = @ExampleObject(value = "{'message': 'unregistered customer or invalid account type'}"))),

    })
    @PostMapping("/create")
    public ResponseEntity<AccountCreationResDTO> createAccount(@RequestBody CreateAccountDTO createAccountDTO){
        
        return new ResponseEntity<>(accountService.saveAccount(createAccountDTO), HttpStatus.CREATED);
    }
    @Operation(summary = "Get total balance for all accounts in the bank")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "balance successfully fetched",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BalanceDTO.class))}),

    })

    @GetMapping("/total-accounts-balance")
    private ResponseEntity<BalanceDTO> getTotalSavingsForAllCustomer(){
        return new ResponseEntity<>(accountService.getAllCustomersTotalSavings(), HttpStatus.OK);
    }




        @Operation(summary = "get balance for a customer particular account")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "balance fetched successfully",
                        content = {@Content(mediaType = "application/json",
                                schema = @Schema(implementation = BalanceDTO.class))}),
                @ApiResponse(responseCode = "404", description = "account not found",
                        content = @Content(examples = @ExampleObject(value = "{'message': 'account not found'}"))),

        })
        @PostMapping("/customer-balance")
        private ResponseEntity<BalanceDTO> getTotalSavingsPerCustomer(@Valid @Validated @RequestBody BalanceInquiryDTO balanceInquiryDTO){
            return new ResponseEntity<>(accountService.getCustomerAccountBalance(balanceInquiryDTO), HttpStatus.OK);
        }


}
