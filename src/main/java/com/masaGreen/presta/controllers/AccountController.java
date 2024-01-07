package com.masaGreen.presta.controllers;

import com.masaGreen.presta.dtos.account.AccountCreationResDTO;
import com.masaGreen.presta.dtos.account.CreateAccountDTO;
import com.masaGreen.presta.dtos.transactions.BalanceDTO;
import com.masaGreen.presta.dtos.transactions.BalanceInquiryDTO;
import com.masaGreen.presta.services.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/account")
@RequiredArgsConstructor
@Tag(name = "Accounts", description = "accounts management endpoints")
public class AccountController {

    private final AccountService accountService;

    @Operation(summary = "Register a new account for AppUser")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account successfully created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AccountCreationResDTO.class))}),
            @ApiResponse(responseCode = "404", description = "AppUser must be registered to create account or provide valid account type", content = @Content(examples = @ExampleObject(value = "{'message': 'unregistered AppUser or invalid account type'}"))),

    })
    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_STAFF')")
    public ResponseEntity<AccountCreationResDTO> createAccount(@RequestBody CreateAccountDTO createAccountDTO) {

        return new ResponseEntity<>(accountService.saveAccount(createAccountDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Get total balance for all accounts in the bank")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "balance successfully fetched", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BalanceDTO.class))}),

    })

    @GetMapping("/total-accounts-balance")
    @PreAuthorize("hasRole('ROLE_STAFF')")
    public ResponseEntity<BalanceDTO> getTotalSavingsForAllAppUser() {
        return new ResponseEntity<>(accountService.getAllAppUsersTotalSavings(), HttpStatus.OK);
    }

    @Operation(summary = "get balance for a AppUser particular account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "balance fetched successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BalanceDTO.class))}),
            @ApiResponse(responseCode = "404", description = "account not found", content = @Content(examples = @ExampleObject(value = "{'message': 'account not found'}"))),

    })
    @PostMapping("/app-user-balance")
    public ResponseEntity<BalanceDTO> getTotalSavingsPerAppUser(
            @Valid @Validated @RequestBody BalanceInquiryDTO balanceInquiryDTO) {
        return new ResponseEntity<>(accountService.getAppUserAccountBalance(balanceInquiryDTO), HttpStatus.OK);
    }

}
