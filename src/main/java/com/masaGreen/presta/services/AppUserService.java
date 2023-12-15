package com.masaGreen.presta.services;

import com.masaGreen.presta.dtos.appUser.CreateAppUser;
import com.masaGreen.presta.dtos.appUser.AppUserDTO;
import com.masaGreen.presta.dtos.appUser.AppUserUpdateDto;
import com.masaGreen.presta.models.entities.AppUser;
import com.masaGreen.presta.repositories.AppUserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AppUser findAppUserById(String id) {
        return appUserRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("AppUser not found"));
    }

    public String saveAppUser(CreateAppUser createAppUser) {

        //does AppUser exist else throw exception
        boolean isExistent = appUserRepository.existsByIdNumber(createAppUser.idNumber());
        if (!isExistent) {
            AppUser appUser = new AppUser();
            appUser.setFirstName(createAppUser.firstName());
            appUser.setLastName(createAppUser.lastName());
            appUser.setPhoneNumber(createAppUser.phoneNumber());
            appUser.setIdNumber(createAppUser.idNumber());
            appUser.setEmail(createAppUser.email());
            appUser.setRoles(appUser.addRoles(createAppUser.roles()));


            //setPin random string + four digits
            String encrypter = UUID.randomUUID().toString();
            String partialPin = generateInitialPin();
            appUser.setPinEncryption(encrypter);
            appUser.setPin(passwordEncoder.encode(partialPin + encrypter));
            appUser.setValidationString(encrypter.substring(0,5));
            appUser.setVerified(false);
            appUserRepository.save(appUser);
            return "AppUser saved successfully";


        } else {
            throw new EntityExistsException("AppUser already exists");
        }

    }


    public List<AppUserDTO> getAllAppUsers() {
        return appUserRepository.findAll().stream().map(AppUserDTO::new).toList();
    }

    public String updateAppUserByIdNumber(String idNumber, AppUserUpdateDto appUserUpdateDto) {
        //exists or throw error
        AppUser appUser = appUserRepository.findByIdNumber(idNumber).orElseThrow(() -> new EntityNotFoundException("AppUser not found"));
        if (appUserUpdateDto.getEmail() != null && !appUserUpdateDto.getEmail().equals(appUser.getEmail())) {
            appUser.setEmail(appUserUpdateDto.getEmail());
        }
        if (appUserUpdateDto.getPhoneNumber() != null && !appUser.getPhoneNumber().equals(appUserUpdateDto.getPhoneNumber())) {
            appUser.setPhoneNumber(appUserUpdateDto.getPhoneNumber());
        }

        appUserRepository.save(appUser);
        return "AppUser update successful";

    }

    public AppUserDTO findByIdNumber(String idNumber) {
        var AppUser = appUserRepository.findByIdNumber(idNumber)
                .orElseThrow(() -> new EntityNotFoundException(String.format("AppUser by %s not found", idNumber)));
        return new AppUserDTO(AppUser);
    }

    @Transactional
    public String deleteAppUserById(String id) {
        //check user permissions
        appUserRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("AppUser by %s not found", id)));
        //check if the AppUser has credit and process by the set out regulations before closing account

        try {
            appUserRepository.deleteById(id);
            return "deleted successfully";
        } catch (Exception ex) {
            //log
            return "something went wrong, not deleted";
        }
    }

    public String validateAppUser(String validationString){
        return null;
        //get user from requst and compare their validatio string and approve them
    }

    private String generateInitialPin() {

        SecureRandom secureRandom = new SecureRandom();
        int generatedRandomInt = secureRandom.nextInt(10000);

        return String.format("%04d", generatedRandomInt);
    }


}
