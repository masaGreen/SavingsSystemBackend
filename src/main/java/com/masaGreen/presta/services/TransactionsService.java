package com.masaGreen.presta.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masaGreen.presta.models.Customer;
import com.masaGreen.presta.models.Transaction;
import com.masaGreen.presta.repositories.TransactionsRepository;

import jakarta.transaction.Transactional;

@Service
public class TransactionsService {
    @Autowired
    private TransactionsRepository transactionsRepository;
    @Autowired
    private CustomerService customerService;

    
    public TransactionsService(TransactionsRepository transactionsRepository2, CustomerService customerService2) {
    }
    @Transactional
    public Transaction saveDepositTransaction(Transaction transaction) {

    //find  the last transaction by this memberNumber 
    //if there is one update their savings amount and save the 
    // transaction else save it as their first transaction

       List<Transaction> transactions = transactionsRepository.findAllByMemberNumber(transaction.getMemberNumber());
       
       if(transactions.size() > 0){
        Collections.reverse(transactions);
        Transaction lastTransaction = transactions.get(0);
        transaction.setCurrentAmount(lastTransaction.getCurrentAmount().add(transaction.getAmount()));
        return transactionsRepository.save(transaction);
        // transaction.incrementBalance(lastTransaction.getAmount());
       } else{
        transaction.setCurrentAmount(transaction.getAmount());
        return transactionsRepository.save(transaction);
       }
        
    
       
    }
    @Transactional
    public Transaction saveWithdrawTransaction(Transaction transaction) {
        //to withdraw a member need to have met certain conditions 
        //i have implemented 1, namely must have saved more than the withdrawable amount 
        List<Transaction> transactions = transactionsRepository.findAllByMemberNumber(transaction.getMemberNumber());
       
        if(transactions.size() > 0){
            Collections.reverse(transactions);
            Transaction lastTransaction = transactions.get(0);
            if(lastTransaction.getCurrentAmount().compareTo(transaction.getAmount()) > 0){

                transaction.setCurrentAmount(lastTransaction.getCurrentAmount().subtract(transaction.getAmount()));
                return transactionsRepository.save(transaction);
            }else{
                return null;
            }
        
        } else{
            return null;
       }
        
    }
    public BigDecimal getSingleMemberTotalSavings(String memberNumber) {


        //query for the latest transaction and check the current savings Amount ;
        //take into account the not had transactions member
         List<Transaction> transactions = transactionsRepository.findAllByMemberNumber(Long.parseLong(memberNumber));
         if(transactions.size() > 0){
            Collections.reverse(transactions);
            Transaction latestTransaction = transactions.get(0);
            return latestTransaction.getCurrentAmount();
        } else {
            return BigDecimal.ZERO;
        } 
}
    public BigDecimal getAllMembersTotalSavings(){
        //total sum of every member latest transaction current savings amount
        List<Customer> customers = customerService.getAllCustomers();
              
        if(customers.size() > 1){
            List<Long> memberNumbers = customers.stream().map(customer -> customer.getMemberNumber()).collect(Collectors.toList());
            List<BigDecimal> savings = new ArrayList<>();
            for(int i = 0; i<memberNumbers.size(); i++){
                savings.add(getSingleMemberTotalSavings(String.valueOf(memberNumbers.get(i))));
                
            }
            BigDecimal total = savings.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
         
                       
            return total;
        }else{
            Customer customer = customers.get(0);
           
            return getSingleMemberTotalSavings(String.valueOf(customer.getMemberNumber()));
          
        }
    }

    public List<Transaction> getAllTransactions(){
        return transactionsRepository.findAll();
    }
    public List<Transaction> getAllTransactionsByMember(String memberNumber){
        return transactionsRepository.findAllByMemberNumber(Long.parseLong(memberNumber));
    }
    
}
