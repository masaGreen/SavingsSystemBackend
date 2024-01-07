package com.masaGreen.presta.services;

import com.masaGreen.presta.dtos.accountType.CreateAccountTypeDTO;
import com.masaGreen.presta.dtos.accountType.EditAccountType;
import com.masaGreen.presta.models.entities.AccountType;
import com.masaGreen.presta.repositories.AccountTypeRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountTypeService {

    private final AccountTypeRepository accountTypeRepository;

    public AccountType findByAccountTypeByName(String id) {
        return accountTypeRepository.findByName(id)
                .orElseThrow(() -> new EntityNotFoundException("AccountType not found"));

    }

    public String createAccountType(CreateAccountTypeDTO createAccountTypeDTO) {

        // check if the name is already taken else throw exception
        boolean isExistent = accountTypeRepository.existsByName(createAccountTypeDTO.name());

        if (isExistent)
            throw new EntityExistsException("accountType already exists");

        AccountType accountType = new AccountType();
        accountType.setName(createAccountTypeDTO.name());
        accountType.setInterest(createAccountTypeDTO.interest());
        accountType.setMinimumBalance(createAccountTypeDTO.minimumBalance());

        accountTypeRepository.save(accountType);
        return "account type created successfully";
    }

    public List<AccountType> fetchAccountTypes() {

        return accountTypeRepository.findAll();
    }

    public String editAccountType(EditAccountType editAccountType, String name) {

        // does account exist
        AccountType accountType = accountTypeRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Can't edit account that does not exist"));

        if (editAccountType.name() != null && !editAccountType.name().equals(accountType.getName())) {
            accountType.setName(editAccountType.name());
        }
        if (editAccountType.interest() != 0L && editAccountType.interest() != accountType.getInterest()) {
            accountType.setInterest(accountType.getInterest());
        }
        accountTypeRepository.save(accountType);
        return "edited successfully";
    }

}
