package com.masaGreen.presta.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masaGreen.presta.models.SavingsProduct;
import com.masaGreen.presta.repositories.SavingsProductsRepository;

import jakarta.transaction.Transactional;

@Service
public class SavingsProductsService {
    @Autowired
    private SavingsProductsRepository savingsProductsRepository;

    public SavingsProduct saveProduct(SavingsProduct savingsProduct) {
        return savingsProductsRepository.save(savingsProduct);
    }

    public List<SavingsProduct> findAllSavingsProducts() {
        return savingsProductsRepository.findAll();
    }
    @Transactional
    public void deleteBySavingsType(String savingsType) {
       savingsProductsRepository.deleteBySavingsType(savingsType);
    }

    public Optional<SavingsProduct> findBySavingsType(String savingsType) {
        return savingsProductsRepository.findBySavingsType(savingsType);
    }
    
}
