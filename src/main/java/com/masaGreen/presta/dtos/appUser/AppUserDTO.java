package com.masaGreen.presta.dtos.appUser;

import com.masaGreen.presta.models.entities.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AppUserDTO {

    private String id;
    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String email;

    private String pin;

    public AppUserDTO(AppUser appUser) {
        this.id = appUser.getId();
        this.firstName = appUser.getFirstName();
        this.lastName = appUser.getLastName();
        this.phoneNumber = appUser.getPhoneNumber();
        this.email = appUser.getEmail();
        this.pin = appUser.getPin();

    }
}
