// package com.masaGreen.presta.services;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertTrue;

// import java.util.List;
// import java.util.Optional;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mockito;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;

// import com.masaGreen.presta.models.Customer;
// import com.masaGreen.presta.repositories.CustomerRepository;

// @SpringBootTest
// public class CustomerServiceTest {

//     private long id;
   
//     private Customer customer;
//     @Autowired
//     CustomerService customerService;
//     @MockBean
//     CustomerRepository customerRepository;

//     @BeforeEach
//     void setUp(){
//         id = 1;
//         customer = Customer.builder()
//             .customerId(id)
//             .firstName("John")
//             .lastName("Doe")
//             .idNumber("12431555")
//             .phoneNumber("0712345678")
//             .email("johndoe@gmail.com")
//             .memberNumber(1)
//         .build();
//     }
//     @Test
//     void testDeleteByMemberNumber() {
        
//         Mockito.when(customerRepository.save(customer)).thenReturn(customer);
//         customerService.deleteByMemberNumber(id);
//         assertTrue(customerService.getAllCustomers().size() == 0);
//     }

//     @Test
//     void testFindByMemberNumber() {
//         Mockito.when(customerRepository.findByMemberNumber(1)).thenReturn(Optional.of(customer));
//        assertTrue(customerService.findByMemberNumber(1).isPresent());
//     }

//     @Test
//     void testGetAllCustomers() {
//         Mockito.when(customerRepository.findAll()).thenReturn(List.of(customer));
       
//         assertTrue(customerService.getAllCustomers().size() == 1);
//         assertEquals(customerService.getAllCustomers().get(0).getCustomerId(), 1);

//     }

//     @Test
//     void testSaveCustomer() {
//         Mockito.when(customerRepository.save(customer)).thenReturn(customer);
//         assertEquals(customerService.saveCustomer(customer).getCustomerId(), 1);
//     }
// }
