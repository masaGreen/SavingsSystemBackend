// package com.masaGreen.presta.services;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertTrue;

// import java.math.BigDecimal;
// import java.util.Arrays;
// import java.util.Collections;
// import java.util.Date;
// import java.util.List;


// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mockito;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;

// import com.masaGreen.presta.models.entities.Customer;
// import com.masaGreen.presta.models.entities.AccountType;
// import com.masaGreen.presta.models.entities.Transaction;
// import com.masaGreen.presta.repositories.TransactionsRepository;

// @SpringBootTest
// public class TransactionsServiceTest {
//     private long id;
//     private Transaction transaction;
//     private Transaction transaction2;

//     @Autowired
//     private TransactionsService transactionsService;
//     @MockBean
//     private CustomerService customerService;
//     @MockBean
//     private TransactionsRepository transactionsRepository;

//     @BeforeEach
//     void setUp(){
//         id=1;
//         BigDecimal bd = new BigDecimal(20);
       
//         AccountType savingsProduct1 = AccountType.builder()
//                             .productId(id)
//                             .savingsType("Vacation")
//                             .date(new Date()) 
//                             .balance(BigDecimal.ZERO)           
//                         .build();
//         transaction = Transaction.builder()
//                             .transactionId(id)
//                             .memberNumber(id)
//                             .amount(bd)
//                             .currentAmount(bd)
//                             .date(new Date())
//                             .methodOfPayment("Cash")
//                             .savingsProduct(savingsProduct1)
//             .build();
//         transaction2 = Transaction.builder()
//                             .transactionId(2L)
//                             .memberNumber(2L)
//                             .amount(bd)
//                             .currentAmount(bd)
//                             .date(new Date())
//                             .methodOfPayment("Cash")
//                             .savingsProduct(savingsProduct1)
//             .build();
        
//     }
//     @Test
//     void testGetAllMembersTotalSavings() {
        
//         Customer customer1 = Customer.builder()
//                             .customerId(1L)
//                             .firstName("John")
//                             .lastName("Doe")
//                             .idNumber("12431555")
//                             .phoneNumber("0712345678")
//                             .email("johndoe@gmail.com")
//                             .memberNumber(1L)
//                         .build();
//         Customer customer2 = Customer.builder()
//                         .customerId(2L)
//                         .firstName("Jane")
//                         .lastName("Doe")
//                         .idNumber("12431556")
//                         .phoneNumber("0712345679")
//                         .email("janedoe@gmail.com")
//                         .memberNumber(2L)
//                     .build();              
//        List<Customer> customers = Arrays.asList(
//             customer1,
//             customer2
//         );


//         Mockito.when(customerService.getAllCustomers()).thenReturn(customers);

//         List<Transaction> transactions = Collections.singletonList(transaction);
//         List<Transaction> transactions2 = Collections.singletonList(transaction2);
        
//         Mockito.when(transactionsRepository.findAllByMemberNumber(1L)).thenReturn(transactions);
//         Mockito.when(transactionsRepository.findAllByMemberNumber(2L)).thenReturn(transactions2);
      

//         assertEquals(new BigDecimal(40), transactionsService.getAllMembersTotalSavings());

//     }

//     @Test
//     void testGetAllTransactions() {
     
//         Mockito.when(transactionsService.saveDepositTransaction(transaction)).thenReturn(transaction);  
//         Mockito.when(transactionsRepository.findAll()).thenReturn(List.of(transaction));

//         assertTrue(transactionsService.getAllTransactions().size() == 1);
       
//     }

//     @Test
//     void testGetAllTransactionsByMember() {
//         Mockito.when(transactionsRepository.findAllByMemberNumber(id)).thenReturn(List.of(transaction));

//         assertEquals(transactionsService.getAllTransactionsByMember("1").size(), 1);
        
//     }

//     @Test
//     void testGetSingleMemberTotalSavings() {
          
//            List<Transaction> transactions = List.of(transaction);
   
//            Mockito.when(transactionsRepository.findAllByMemberNumber(Long.parseLong("1"))).thenReturn(transactions);
   
          
//            assertEquals(transaction.getCurrentAmount(), transactionsService.getSingleMemberTotalSavings("1"));
//     }

//     @Test
//     void testSaveDepositTransaction() {
//         Mockito.when(transactionsRepository.save(transaction)).thenReturn(transaction);
//         assertEquals(transactionsService.saveDepositTransaction(transaction).getCurrentAmount(), new BigDecimal(20));
//     }

//     @Test
//     void testSaveWithdrawTransaction() {
//         Mockito.when(transactionsRepository.save(transaction)).thenReturn(transaction);
//         assertEquals(transactionsService.saveWithdrawTransaction(transaction), null);
//     }
// }
