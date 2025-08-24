package com.yzn.loans.service;

import com.yzn.loans.dto.LoansDTO;
import com.yzn.loans.repository.LoanRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;

    public List<LoansDTO> getLoans() {
        List<LoansDTO> loans = loanRepository.findAll();

    }
}
