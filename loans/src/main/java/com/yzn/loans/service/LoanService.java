package com.yzn.loans.service;

import com.yzn.loans.dto.LoansDTO;
import com.yzn.loans.exception.LoanAlreadyExistsException;
import com.yzn.loans.exception.ResourceNotFoundException;
import com.yzn.loans.mappers.LoansMapper;
import com.yzn.loans.model.Loans;
import com.yzn.loans.repository.LoanRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;

    public List<LoansDTO> getLoans() {
        return loanRepository.findAll()
                .stream()
                .map(
                        loans -> LoansMapper.mapToLoansDTO(Optional.ofNullable(loans), new LoansDTO())
                )
                .toList();
    }

    public LoansDTO getLoanDetails(String mobileNumber){
        Optional<Loans> loans = loanRepository.findByMobileNumber(mobileNumber);
        if(loans.isEmpty()){
            throw new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber);
        }
        return LoansMapper.mapToLoansDTO(loans, new LoansDTO());
    }

    public void createLoan(String mobileNumber) {
        Optional<Loans> optionalLoans= loanRepository.findByMobileNumber(mobileNumber);
        if(optionalLoans.isPresent()){
            throw new LoanAlreadyExistsException("Loan already registered with given mobileNumber "+mobileNumber);
        }
        loanRepository.save(createNewLoan(mobileNumber));
    }


    private Loans createNewLoan(String mobileNumber) {
        Loans newLoan = new Loans();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType("Home Loan");
        newLoan.setTotalLoan(1_00_000);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(1_00_000);
        return newLoan;
    }

    public void deleteLoan(String mobileNumber){
        Loans loans = loanRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
                );
        loanRepository.deleteById(loans.getLoanId());
    }

    public LoansDTO updateLoan(LoansDTO loansDTO){
       Loans loans = loanRepository.findByLoanNumber(loansDTO.getLoanNumber())
               .orElseThrow(() -> new ResourceNotFoundException("Loan", "mobileNumber", loansDTO.getMobileNumber())
               );
       loans.setLoanType(loansDTO.getLoanType());
       loans.setTotalLoan(loansDTO.getTotalLoan());
       loans.setAmountPaid(loansDTO.getAmountPaid());
       loans.setOutstandingAmount(loansDTO.getOutstandingAmount());
       loanRepository.save(loans);
       return LoansMapper.mapToLoansDTO(Optional.of(loans), new LoansDTO());
    }
}

