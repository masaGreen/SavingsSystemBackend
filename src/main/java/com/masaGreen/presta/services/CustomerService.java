package com.masaGreen.presta.services;

import com.masaGreen.presta.dtos.customer.CreateCustomer;
import com.masaGreen.presta.dtos.customer.CustomerDTO;
import com.masaGreen.presta.dtos.customer.CustomerUpdateDto;
import com.masaGreen.presta.models.entities.Customer;
import com.masaGreen.presta.repositories.CustomerRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer findCustomerById(String id) {
        return customerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("customer not found"));
    }

    public String saveCustomer(CreateCustomer createCustomer) {

        //does customer exist else throw exception
        boolean isExistent = customerRepository.existsByIdNumber(createCustomer.idNumber());
        if (!isExistent) {
            Customer customer = new Customer();
            customer.setFirstName(createCustomer.firstName());
            customer.setLastName(createCustomer.lastName());
            customer.setPhoneNumber(createCustomer.phoneNumber());
            customer.setIdNumber(createCustomer.idNumber());
            customer.setEmail(createCustomer.email());

            //setPin random string + four digits
            String encrypter = UUID.randomUUID().toString();
            String partialPin = generateInitialPin();
            customer.setPinEncryption(encrypter);
            customer.setPin(partialPin + encrypter);
            customerRepository.save(customer);
            return "customer saved successfully";


        } else {
            throw new EntityExistsException("customer already exists");
        }

    }


    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream().map(CustomerDTO::new).toList();
    }

    public String updateCustomerByIdNumber(String idNumber, CustomerUpdateDto customerUpdateDto) {
        //exists or throw error
        Customer customer = customerRepository.findByIdNumber(idNumber).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        if (customerUpdateDto.getEmail() != null && !customerUpdateDto.getEmail().equals(customer.getEmail())) {
            customer.setEmail(customerUpdateDto.getEmail());
        }
        if (customerUpdateDto.getPhoneNumber() != null && !customer.getPhoneNumber().equals(customerUpdateDto.getPhoneNumber())) {
            customer.setPhoneNumber(customerUpdateDto.getPhoneNumber());
        }

        customerRepository.save(customer);
        return "customer update successful";

    }

    public CustomerDTO findByIdNumber(String idNumber) {
        var customer = customerRepository.findByIdNumber(idNumber)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Customer by %s not found", idNumber)));
        return new CustomerDTO(customer);
    }

    @Transactional
    public String deleteCustomerById(String id) {
        //check user permissions
        customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Customer by %s not found", id)));
        //check if the customer has credit and process by the set out regulations before closing account

        try {
            customerRepository.deleteById(id);
            return "deleted successfully";
        } catch (Exception ex) {
            //log
            return "something went wrong, not deleted";
        }
    }

    private String generateInitialPin() {

        SecureRandom secureRandom = new SecureRandom();
        int generatedRandomInt = secureRandom.nextInt(10000);

        return String.format("%04d", generatedRandomInt);
    }

}
