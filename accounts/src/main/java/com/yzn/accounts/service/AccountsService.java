package com.yzn.accounts.service;

import com.yzn.accounts.repository.AccountsRepository;
import com.yzn.accounts.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountsService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;


    public void createAccount() {

}

}
