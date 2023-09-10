package com.masaGreen.presta.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;






@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Customer_id")
    private long customerId;

    @Column(name = "First_name")
    private String firstName;

    @Column(name = "Last_name")
    private String lastName;
  
    @Column(name = "Id_number")
    private String idNumber;
    
    @Column(name = "Phone_number")
    private String phoneNumber;
   
    @Column(name = "Email")
    private String email;

    @Column(name="Date")
    @Temporal(TemporalType.DATE)
    private Date dateOfJoin;

    @Column(name="Member_number", unique = true)
    private long memberNumber;
}
