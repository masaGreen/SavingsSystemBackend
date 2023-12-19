package com.masaGreen.presta.services;

import com.masaGreen.presta.dtos.appUser.CreateAppUser;
import com.masaGreen.presta.dtos.appUser.LoginResDTO;
import com.masaGreen.presta.dtos.appUser.PinEditDTO;
import com.masaGreen.presta.ExceptionsHandling.exceptions.UnverifiedUserException;
import com.masaGreen.presta.dtos.appUser.AppUserDTO;
import com.masaGreen.presta.dtos.appUser.AppUserLoginDTO;
import com.masaGreen.presta.dtos.appUser.AppUserUpdateDto;
import com.masaGreen.presta.models.entities.AppUser;
import com.masaGreen.presta.models.entities.Role;
import com.masaGreen.presta.repositories.AppUserRepository;
import com.masaGreen.presta.repositories.RoleRepository;
import com.masaGreen.presta.security.jwt.JwtFilter;
import com.masaGreen.presta.security.jwt.JwtService;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
  
    public AppUser getAppUserFromServletRequest(HttpServletRequest request) {
        String idNumber = (String) request.getAttribute("idNumber");
        if (idNumber == null)
            throw new AccessDeniedException("Access denied");
        return appUserRepository.findByIdNumber(idNumber).orElseThrow(
                () -> new EntityNotFoundException(" app user not found"));
    }

    public AppUser findAppUserById(String id) {
        return appUserRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("AppUser not found"));
    }

    public String saveAppUser(CreateAppUser createAppUser) {

        // does AppUser exist else throw exception
        boolean isExistent = appUserRepository.existsByIdNumber(createAppUser.idNumber());
        if (!isExistent) {

            AppUser appUser = new AppUser();
            appUser.setFirstName(createAppUser.firstName());
            appUser.setLastName(createAppUser.lastName());
            appUser.setPhoneNumber(createAppUser.phoneNumber());
            appUser.setIdNumber(createAppUser.idNumber());
            appUser.setEmail(createAppUser.email());
            
            if (createAppUser.roles() != null) {
                Set<Role> newRoles = createAppUser.roles().stream()
                .map(s -> new Role(s))
                .collect(Collectors.toSet());
                //addDefaultRole
                newRoles.addAll(addDefaultRole());
                appUser.setRoles(newRoles);
                saveNewRoles(newRoles);

            }else{
                appUser.setRoles(addDefaultRole());
            }

            String validationString = UUID.randomUUID().toString();
            appUser.setValidationString(validationString);
            System.out.println(validationString);
            appUser.setVerified(false);

            AppUser savedUser = appUserRepository.save(appUser);
            // emailService.sendEmailVerificationRequest(savedUser);

            return "AppUser saved successfully";

        } else {
            throw new EntityExistsException("AppUser already exists");
        }

    }

    public String validateAppUser(String code) {
        AppUser appUser = appUserRepository.findByValidationString(code).orElseThrow(
                () -> new EntityNotFoundException("incorrect validation code"));
        if (!appUser.isVerified()) {
            appUser.setVerified(true);
            appUserRepository.save(appUser);
            return "user verified successfully";
        }
        return "user has already been verified";
    }

    public LoginResDTO loginByEmailAndIdNUmber(AppUserLoginDTO appUserLoginDTO) {
        AppUser appUser = appUserRepository.findByIdNumber(appUserLoginDTO.idNumber()).orElseThrow(
                () -> new BadCredentialsException("bad credentials"));

        if (!appUser.isVerified()) {
            throw new UnverifiedUserException("must be verified to login");
        }

        if (appUser.getPin() == null) {
            // send them their pin via email
            // setPin random string + four digits
            String encrypter = UUID.randomUUID().toString();
            String partialPin = generateInitialPin();

            appUser.setPinEncryption(encrypter);
            appUser.setPin(passwordEncoder.encode(partialPin + encrypter));
            appUserRepository.save(appUser);
            // emailService.sendPinInfoEmail(appUser.getEmail(), partialPin,
            // appUser.getFirstName());

        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(appUser.getIdNumber(),
                appUser.getPin());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtService.generateToken(appUser.getIdNumber());

        return new LoginResDTO(appUser.getId(), token, appUser.getFirstName());
    }

    public String changePin(HttpServletRequest request, PinEditDTO pinEditDTO) {

        AppUser appUser = getAppUserFromServletRequest(request);
        if (passwordEncoder.matches(pinEditDTO.oldPin() + appUser.getPinEncryption(), appUser.getPin())) {
            appUser.setPin(passwordEncoder.encode(pinEditDTO.newPin() + appUser.getPinEncryption()));
        }
        appUserRepository.save(appUser);
        return "pin successfully changed";
    }

    public List<AppUser> getAllAppUsers() {
        
        return appUserRepository.findAll();
        // return appUserRepository.findAll().stream().map(AppUserDTO::new).toList();

     

    }

    public String updateAppUserByIdNumber(String idNumber, AppUserUpdateDto appUserUpdateDto) {
        // exists or throw error
        AppUser appUser = appUserRepository.findByIdNumber(idNumber)
                .orElseThrow(() -> new EntityNotFoundException("AppUser not found"));
        if (appUserUpdateDto.getEmail() != null && !appUserUpdateDto.getEmail().equals(appUser.getEmail())) {
            appUser.setEmail(appUserUpdateDto.getEmail());
        }
        if (appUserUpdateDto.getPhoneNumber() != null
                && !appUser.getPhoneNumber().equals(appUserUpdateDto.getPhoneNumber())) {
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

    public AppUser save(AppUser appUser) {
        return appUserRepository.save(appUser);
    }

    @Transactional
    public String deleteAppUserById(String id) {
        // check user permissions
        appUserRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("AppUser by %s not found", id)));
        // check if the AppUser has credit and process by the set out regulations before
        // closing account

        try {
            appUserRepository.deleteById(id);
            return "deleted successfully";
        } catch (Exception ex) {
            // log
            return "something went wrong, not deleted";
        }
    }

    private String generateInitialPin() {

        SecureRandom secureRandom = new SecureRandom();
        int generatedRandomInt = secureRandom.nextInt(10000);

        return String.format("%04d", generatedRandomInt);
    }

    private Set<Role> addDefaultRole() {
        boolean roleExists = roleRepository.existsByName("ROLE_CUSTOMER");
        Set<Role> roles = new HashSet<>();
        if (roleExists) {
            roles.add(new Role("ROLE_CUSTOMER"));
            return roles;
        } else {
            roleRepository.save(new Role("ROLE_CUSTOMER"));
            roles.add(new Role("ROLE_CUSTOMER"));
            return roles;

        }

    }

    public void saveNewRoles(Set<Role> roles) {
      
        

        List<Role> existingRoles =roleRepository.findAll();
       
        Set<Role> rolesSet = new HashSet<>(existingRoles);
        roles.removeAll(rolesSet);

        roleRepository.saveAll(roles);


    }

}
