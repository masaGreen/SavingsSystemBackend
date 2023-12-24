package com.masaGreen.presta.controllers;

import com.masaGreen.presta.dtos.transactions.CreateTransactionDTO;
import com.masaGreen.presta.models.entities.Transaction;
import com.masaGreen.presta.services.TransactionsService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/transactions")
@Tag(name = "Transactions", description = "Manages all transactions")
@RequiredArgsConstructor
public class TransactionController {

        private final TransactionsService transactionsService;

        @Operation(summary = "register a new transaction")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "transaction registered successfully", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) }),
                        @ApiResponse(responseCode = "404", description = "account to initiating transaction not found", content = @Content(examples = @ExampleObject(value = "{'message': 'incorrect account number provided'}"))),

        })
        @PostMapping("/create-transaction")
        public ResponseEntity<String> createDepositTransaction(
                        @RequestBody @Valid CreateTransactionDTO createDepositTransactionDTO) {

                return new ResponseEntity<>(transactionsService.createTransaction(createDepositTransactionDTO),
                                HttpStatus.CREATED);
        }

        @Operation(summary = "fetches all transactions by a AppUser")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "transactions fetched  successfully", content = {
                                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Transaction.class))) }),
                        @ApiResponse(responseCode = "404", description = "AppUser not found", content = @Content(examples = @ExampleObject(value = "{'message': 'AppUser not found'}"))),

        })
        @GetMapping("/allTransactions-by-AppUser/{idNumber}")
        public ResponseEntity<List<Transaction>> getAllTransactionsByAppUser(@PathVariable String idNumber) {
                return new ResponseEntity<>(transactionsService.getAllTransactionsByAppUser(idNumber), HttpStatus.OK);
        }

        @Operation(summary = "fetches all transactions ")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "transactions fetched  successfully", content = {
                                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Transaction.class))) }),

        })
        @GetMapping("/all-transactions")
        @PreAuthorize("hasRole('ROLE_STAFF')")
        public ResponseEntity<List<Transaction>> getAllTransactions() {
                return new ResponseEntity<>(transactionsService.getAllTransactions(), HttpStatus.OK);
        }

        @Operation(summary = "fetches all transactions by an accountNumber")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "transactions fetched  successfully", content = {
                                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Transaction.class))) }),
                        @ApiResponse(responseCode = "404", description = "incorrect account number", content = @Content(examples = @ExampleObject(value = "{'message': 'incorrect account number'}"))),

        })
        @GetMapping("/all-transactions-by-accountNumber/{accountNumber}")
        @PreAuthorize("hasRole('ROLE_STAFF')")
        public ResponseEntity<List<Transaction>> getAllTransactionsByAccountNumber(
                        @PathVariable String accountNumber) {
                return new ResponseEntity<>(transactionsService.getAllTRansactionsByAccountNumber(accountNumber),
                                HttpStatus.OK);
        }

}
