package com.yzn.accounts.service;

import com.yzn.accounts.dto.AccountsDTO;
import com.yzn.accounts.dto.CustomerDTO;
import com.yzn.accounts.exception.CustomerAlreadyExistsException;
import com.yzn.accounts.exception.ResourceNotFoundException;
import com.yzn.accounts.mapper.AccountsMapper;
import com.yzn.accounts.mapper.CustomerMapper;
import com.yzn.accounts.model.Accounts;
import com.yzn.accounts.model.Customer;
import com.yzn.accounts.repository.AccountsRepository;
import com.yzn.accounts.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountsService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;


    public void createAccount(CustomerDTO customerDTO) {
        Customer customer = CustomerMapper.mapToCustomer(customerDTO, new Customer());
        Optional<Customer> existingCustomer = customerRepository.findByMobileNumber(customer.getMobileNumber());

        if (existingCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer with mobile number " + customer.getMobileNumber() + " already exists");
        }

        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("Admin");
        Customer savedCustomer = customerRepository.save(customer);

        accountsRepository.save(createAccount(savedCustomer));
}

    public Accounts createAccount(Customer customer) {
        Accounts account = new Accounts();
        account.setCustomerId(customer.getCustomerId());
        long accNumber = (long) Math.floor(Math.random() * 9000000000L) + 1000000000L;

        account.setAccountNumber(accNumber);
        account.setAccountType("Savings");
        account.setBranchAddress("Amman, Jordan");

        account.setCreatedAt(LocalDateTime.now());
        account.setCreatedBy("Admin");

        return account;
    }

    public CustomerDTO getAccountDetails(String mobileNumber) {

    Customer customer = customerRepository.findByMobileNumber(mobileNumber)
            .orElseThrow(() -> new ResourceNotFoundException("Customer", "Mobile Number", mobileNumber));

    Accounts account = accountsRepository.findByCustomerId(customer.getCustomerId())
            .orElseThrow(() -> new ResourceNotFoundException("Account", "Customer Id", customer.getCustomerId().toString()));

    CustomerDTO customerDTO = CustomerMapper.mapToCustomerDto(customer, new CustomerDTO());
    customerDTO.setAccountsDTO(AccountsMapper.mapToAccountsDto(account, new AccountsDTO()));

    return customerDTO;
    }

    public CustomerDTO updateAccount(CustomerDTO customerDTO) {
       AccountsDTO accountsDTO = customerDTO.getAccountsDTO();

       if(accountsDTO != null) {
           Accounts accounts = accountsRepository.findById(accountsDTO.getAccountNumber())
                   .orElseThrow(() -> new ResourceNotFoundException("Account", "Account Number", accountsDTO.getAccountNumber().toString()));

           AccountsMapper.mapToAccounts(accountsDTO, accounts);
           accounts = accountsRepository.save(accounts);

           Long customerId = accounts.getCustomerId();
           Customer customer = customerRepository.findById(customerId)
                   .orElseThrow(() -> new ResourceNotFoundException("Customer", "Customer Id", customerId.toString()));

           CustomerMapper.mapToCustomer(customerDTO, customer);
           customer = customerRepository.save(customer);

           CustomerDTO customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDTO());
       }
       return customerDTO;
    }
}
