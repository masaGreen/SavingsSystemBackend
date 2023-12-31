package com.masaGreen.presta.controllers;


import com.masaGreen.presta.dtos.appUser.*;
import com.masaGreen.presta.services.AppUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ComponentScan(basePackages = "com.masaGreen.presta")
@RestController
@RequestMapping("/v1/app-user")
@RequiredArgsConstructor
@Tag(name = "AppUsers", description = "Endpoints for managing AppUsers")
public class AppUserController {


    private final AppUserService appUserService;


    @Operation(summary = "register a new AppUser")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "AppUser registered successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "409", description = "AppUser already exists",
                    content = @Content(examples = @ExampleObject(value = "{'message': 'AppUser already exists'}"))),

    })
    @PostMapping("/create")
    public ResponseEntity<String> saveAppUser(@RequestBody @Valid CreateAppUser createAppUser) {

        return new ResponseEntity<>(appUserService.saveAppUser(createAppUser), HttpStatus.CREATED);

    }

    @Operation(summary = "login app-user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "AppUser logged in successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "401", description = "Bad credentials",
                    content = @Content(examples = @ExampleObject(value = "{'message': 'Incorrect credentials'}"))),

    })
    @PostMapping("/login")
    public ResponseEntity<LoginResDTO> loginAppUser(@RequestBody AppUserLoginDTO appUserLoginDTO) {

        return new ResponseEntity<>(appUserService.loginByIdNumberAndPin(appUserLoginDTO), HttpStatus.OK);
    }

    @Operation(summary = "validate app-user by mail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "AppUser validated successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "404", description = "AppUser not found",
                    content = @Content(examples = @ExampleObject(value = "{'message': 'AppUser not found'}"))),

    })
    @GetMapping("/validate-app-user/{validationString}")
    public ResponseEntity<String> validateAppUserByMail(@PathVariable String validationString) {

        return new ResponseEntity<>(appUserService.validateAppUser(validationString), HttpStatus.OK);
    }

    @Operation(summary = "resend validation-code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "validation code sent successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "404", description = "AppUser not found",
                    content = @Content(examples = @ExampleObject(value = "{'message': 'AppUser not found'}"))),

    })
    @GetMapping("/validate-app-user/{email}")
    public ResponseEntity<String> resendValidationCode(@PathVariable String email) {

        return new ResponseEntity<>(appUserService.validateAppUser(email), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "AppUser validated successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "404", description = "AppUser not found",
                    content = @Content(examples = @ExampleObject(value = "{'message': 'AppUser not found'}"))),

    })
    @PostMapping("/change-pin")
    public ResponseEntity<String> changePin(@RequestBody @Valid @Validated PinEditDTO pinEditDTO, HttpServletRequest request) {
        return new ResponseEntity<>(appUserService.changePin(request, pinEditDTO), HttpStatus.OK);
    }

    @Operation(summary = "fetch all AppUsers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "AppUsers fetched successfully",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = AppUserDTO.class)))})
    })
    @GetMapping("/all-app-users")
    @PreAuthorize("hasRole('ROLE_STAFF')")
    public ResponseEntity<List<AppUserDTO>> getAllAppUsers() {


        return new ResponseEntity<>(appUserService.getAllAppUsers(), HttpStatus.OK);
    }


    @Operation(summary = "fetch AppUser by id-number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "AppUser fetched successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AppUserDTO.class))}),
            @ApiResponse(responseCode = "404", description = "AppUser not found",
                    content = @Content(examples = @ExampleObject(value = "{'message': 'AppUser not found'}"))),

    })
    @GetMapping("/findByIdNumber/{idNumber}")
    @PreAuthorize("hasRole('ROLE_STAFF')")
    public ResponseEntity<AppUserDTO> findAppUserByIdNumber(@PathVariable String idNumber) {
        return new ResponseEntity<>(appUserService.findByIdNumber(idNumber), HttpStatus.OK);
    }


    @Operation(summary = "edit AppUser details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "AppUser edited successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "404", description = "AppUser not found",
                    content = @Content(examples = @ExampleObject(value = "{'message': 'AppUser not found'}"))),

    })
    @PutMapping("/update-app-user/{idNumber}")
    public ResponseEntity<String> updateAppUser(@RequestBody @Valid AppUserUpdateDto appUserUpdateDto, @PathVariable String idNumber) {

        return new ResponseEntity<>(appUserService.updateAppUserByIdNumber(idNumber, appUserUpdateDto), HttpStatus.OK);
    }

    @Operation(summary = "close account by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "AppUser details and accounts deleted successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "404", description = "AppUser not found",
                    content = @Content(examples = @ExampleObject(value = "{'message': 'AppUser not found'}"))),

    })
    @DeleteMapping("/close-account/{id}")
    @PreAuthorize("hasRole('ROLE_STAFF')")
    public ResponseEntity<String> deleteAppUser(@PathVariable String id) {
        return new ResponseEntity<>(appUserService.deleteAppUserById(id), HttpStatus.OK);
    }

}
