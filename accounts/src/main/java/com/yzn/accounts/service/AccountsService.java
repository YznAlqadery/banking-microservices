package com.yzn.accounts.service;

import com.yzn.accounts.dto.CustomerDTO;
import com.yzn.accounts.exception.CustomerAlreadyExistsException;
import com.yzn.accounts.mapper.CustomerMapper;
import com.yzn.accounts.model.Accounts;
import com.yzn.accounts.model.Customer;
import com.yzn.accounts.repository.AccountsRepository;
import com.yzn.accounts.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
        return account;
    }

}
