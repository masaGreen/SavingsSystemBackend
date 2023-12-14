package com.masaGreen.presta.dtos.customer;

import com.masaGreen.presta.models.entities.Customer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerDTO {
    
    private String id;
    private String firstName;
   
    private String lastName;
   
    private String phoneNumber;
   
    private String email;

    private String pin;

    public CustomerDTO(Customer customer){
        this.id = customer.getId();
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.phoneNumber=customer.getPhoneNumber();
        this.email = customer.getEmail();
        this.pin = customer.getPin();
        
    }
}
