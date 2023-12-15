package com.masaGreen.presta.controllers;


import com.masaGreen.presta.dtos.appUser.AppUserDTO;
import com.masaGreen.presta.dtos.appUser.AppUserUpdateDto;
import com.masaGreen.presta.dtos.appUser.CreateAppUser;
import com.masaGreen.presta.services.AppUserService;
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
@RequestMapping("/v1/AppUser")
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
    //role must be staff
    public ResponseEntity<String> saveAppUser(@RequestBody @Valid CreateAppUser createAppUser){

        return new ResponseEntity<>(appUserService.saveAppUser(createAppUser), HttpStatus.CREATED);

    }


    @Operation(summary = "fetch all AppUsers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "AppUsers fetched successfully",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema( schema = @Schema(implementation = AppUserDTO.class)))})
    })
    @GetMapping("/all-AppUsers")
    //role must be staff
    public ResponseEntity<List<AppUserDTO>> getAllAppUsers(){
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
    //role must be staff
    private ResponseEntity<AppUserDTO> findAppUserByIdNumber(@PathVariable String idNumber){
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
    @PutMapping("/updateAppUser/{idNumber}")
    private ResponseEntity<String> updateAppUser(@RequestBody @Valid AppUserUpdateDto appUserUpdateDto, @PathVariable String idNumber ){

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
    //role must be stuff
    private ResponseEntity<String> deleteAppUser(@PathVariable String id){
        return new ResponseEntity<>(appUserService.deleteAppUserById(id), HttpStatus.OK);
    }

}