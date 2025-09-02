package com.yzn.accounts.service;

import com.yzn.accounts.dto.AccountsDTO;
import com.yzn.accounts.dto.CardsDTO;
import com.yzn.accounts.dto.CustomerDetailsDTO;
import com.yzn.accounts.dto.LoansDTO;
import com.yzn.accounts.exception.ResourceNotFoundException;
import com.yzn.accounts.mapper.AccountsMapper;
import com.yzn.accounts.mapper.CustomerMapper;
import com.yzn.accounts.model.Accounts;
import com.yzn.accounts.model.Customer;
import com.yzn.accounts.repository.AccountsRepository;
import com.yzn.accounts.repository.CustomerRepository;
import com.yzn.accounts.service.client.CardsFeignClient;
import com.yzn.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;

    public CustomerDetailsDTO getCustomerDetails(String mobileNumber, String correlationId){
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "Mobile Number", mobileNumber));

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Account", "Customer Id", customer.getCustomerId().toString()));

        CustomerDetailsDTO customerDetailsDTO = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDTO());
        customerDetailsDTO.setAccountsDTO(AccountsMapper.mapToAccountsDto(accounts, new AccountsDTO()));

        ResponseEntity<LoansDTO> loansDTOResponseEntity = loansFeignClient.getLoanDetails(correlationId,mobileNumber);
        customerDetailsDTO.setLoansDTO(loansDTOResponseEntity.getBody());

        ResponseEntity<CardsDTO> cardsDTOResponseEntity = cardsFeignClient.getCardDetails(correlationId,mobileNumber);
        customerDetailsDTO.setCardsDTO(cardsDTOResponseEntity.getBody());

        return customerDetailsDTO;
    }
}
