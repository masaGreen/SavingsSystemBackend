package com.masaGreen.presta.controllers;

import com.masaGreen.presta.dtos.accountType.CreateAccountTypeDTO;
import com.masaGreen.presta.dtos.accountType.EditAccountType;
import com.masaGreen.presta.models.entities.AccountType;
import com.masaGreen.presta.services.AccountTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account-type")
@RequiredArgsConstructor
@Tag(name="Account-Type", description = "manages account-types being offered")
//only stuff
public class AccountTypeController {
    
    private final AccountTypeService accountTypeService;
    @Operation(summary = "creates a new account type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "account-type created  successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),

            @ApiResponse(responseCode = "409", description = "account-type already exists",
                    content = @Content(examples = @ExampleObject(value = "{'message': 'account-type already exists'}"))),
    })
    @PostMapping("/create")
    public ResponseEntity<String> createAccountType(@RequestBody CreateAccountTypeDTO createAccountTypeDTO){
        return new ResponseEntity<>(accountTypeService.createAccountType(createAccountTypeDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "fetches all account types")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "account-types fetched  successfully",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = AccountType.class)))}),
              })
    @GetMapping("/get-all-account-types")
    public ResponseEntity<List<AccountType>> getAllAccounts(){
        return new ResponseEntity<>(accountTypeService.fetchAccountTypes(),HttpStatus.OK);
    }
    @Operation(summary = "fetch account-type by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "account-type fetched successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountType.class))}),
            @ApiResponse(responseCode = "404", description = "account not found",
                    content = @Content(examples = @ExampleObject(value = "{'message': 'account not found'}"))),

    })
    @GetMapping("/getbyname/{name}")
    public ResponseEntity<AccountType> getAccountByName(@PathVariable String name){
        return new ResponseEntity<>(accountTypeService.findByAccountTypeByName(name), HttpStatus.OK);
    }
    @Operation(summary = "edit account type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "account-type edited  successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "404", description = "account not found",
                    content = @Content(examples = @ExampleObject(value = "{'message': 'account not found'}"))),

    })
    @PutMapping("/edit-account-type/{name}")
    public ResponseEntity<String> adjustInterest(@RequestBody EditAccountType editAccountType, @PathVariable String name){
        return new ResponseEntity<>(accountTypeService.editAccountType(editAccountType, name), HttpStatus.OK);
    }
}
