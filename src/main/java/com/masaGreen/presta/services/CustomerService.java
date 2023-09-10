package com.masaGreen.presta.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.masaGreen.presta.repositories.CustomerRepository;

import jakarta.transaction.Transactional;

import com.masaGreen.presta.models.Customer;

import java.util.List;
import java.util.Optional;
@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public Customer saveCustomer(Customer customer){
        
            return customerRepository.save(customer);
       
    }
   

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> findByMemberNumber(long memberNumber) {
        return customerRepository.findByMemberNumber(memberNumber);
    }
    @Transactional
    public void deleteByMemberNumber(long memberNumber) {
        customerRepository.deleteByMemberNumber(memberNumber);
    }

    
}
