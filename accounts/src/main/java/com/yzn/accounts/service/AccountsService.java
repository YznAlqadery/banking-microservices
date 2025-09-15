package com.yzn.accounts.service;

import com.yzn.accounts.dto.AccountsDTO;
import com.yzn.accounts.dto.AccountsMessageDTO;
import com.yzn.accounts.dto.CustomerDTO;
import com.yzn.accounts.exception.CustomerAlreadyExistsException;
import com.yzn.accounts.exception.ResourceNotFoundException;
import com.yzn.accounts.mapper.AccountsMapper;
import com.yzn.accounts.mapper.CustomerMapper;
import com.yzn.accounts.model.Accounts;
import com.yzn.accounts.model.Customer;
import com.yzn.accounts.repository.AccountsRepository;
import com.yzn.accounts.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountsService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private final StreamBridge streamBridge;

    private static final Logger log = LoggerFactory.getLogger(AccountsService.class);


    public void createAccount(CustomerDTO customerDTO) {
        Customer customer = CustomerMapper.mapToCustomer(customerDTO, new Customer());
        Optional<Customer> existingCustomer = customerRepository.findByMobileNumber(customer.getMobileNumber());

        if (existingCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer with mobile number " + customer.getMobileNumber() + " already exists");
        }


        Customer savedCustomer = customerRepository.save(customer);

        Accounts savedAccount = accountsRepository.save(createAccount(savedCustomer));

        sendCommunication(savedAccount, savedCustomer);
}

    private void sendCommunication(Accounts accounts, Customer customer){
        AccountsMessageDTO accountsMessageDTO = new AccountsMessageDTO(accounts.getAccountNumber(), customer.getName(),
                customer.getEmail(), customer.getMobileNumber());

        log.info("Sending Communication request for the details: {}", accountsMessageDTO);
        var result = streamBridge.send("sendCommunication-out-0", accountsMessageDTO);
        log.info("Communication request sent successfully: {}", result);
    }

    public boolean updateCommunicationStatus(Long accountNumber){
        boolean isUpdated = false;
        if (accountNumber != null) {
            Accounts accounts = accountsRepository.findById(accountNumber)
                    .orElseThrow(() -> new ResourceNotFoundException("Account", "Account Number", accountNumber.toString()));

            accounts.setCommunicationSw(true);
            accountsRepository.save(accounts);
            isUpdated = true;
        }
        return isUpdated;
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

    public CustomerDTO getAccountDetails(String mobileNumber) {

    Customer customer = customerRepository.findByMobileNumber(mobileNumber)
            .orElseThrow(() -> new ResourceNotFoundException("Customer", "Mobile Number", mobileNumber));

    Accounts account = accountsRepository.findByCustomerId(customer.getCustomerId())
            .orElseThrow(() -> new ResourceNotFoundException("Account", "Customer Id", customer.getCustomerId().toString()));

    CustomerDTO customerDTO = CustomerMapper.mapToCustomerDto(customer, new CustomerDTO());
    customerDTO.setAccountsDTO(AccountsMapper.mapToAccountsDto(account, new AccountsDTO()));

    return customerDTO;
    }

    public List<CustomerDTO> getAllAccounts(){
        List<Customer> customers = customerRepository.findAll();

        List<Accounts> accounts = accountsRepository.findAll();

        return customers.stream().map(customer -> {
            CustomerDTO customerDTO = CustomerMapper.mapToCustomerDto(customer, new CustomerDTO());
            customerDTO.setAccountsDTO(AccountsMapper.mapToAccountsDto(accounts.stream().filter(account -> account.getCustomerId().equals(customer.getCustomerId())).findFirst().get(), new AccountsDTO()));
            return customerDTO;
        }).toList();

    }

    @Transactional
    @Modifying
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

       }
       return customerDTO;
    }

    @Transactional
    @Modifying
    public void deleteAccount(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "Mobile Number", mobileNumber));
        Long customerId = customer.getCustomerId();

        accountsRepository.deleteByCustomerId(customerId);
        customerRepository.deleteById(customerId);
    }

}
