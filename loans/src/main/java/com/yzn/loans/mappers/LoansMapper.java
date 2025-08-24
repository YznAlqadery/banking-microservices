package com.yzn.loans.mappers;

import com.yzn.loans.dto.LoansDTO;
import com.yzn.loans.model.Loans;

import java.util.Optional;

public class LoansMapper {

    public static LoansDTO mapToLoansDTO(Loans loans, LoansDTO loansDTO) {
        loansDTO.setMobileNumber(loans.getMobileNumber());
        loansDTO.setLoanNumber(loans.getLoanNumber());
        loansDTO.setLoanType(loans.getLoanType());
        loansDTO.setTotalLoan(loans.getTotalLoan());
        loansDTO.setAmountPaid(loans.getAmountPaid());
        loansDTO.setOutstandingAmount(loans.getOutstandingAmount());
        return loansDTO;
    }

    public static Loans mapToLoans(LoansDTO loansDTO, Loans loans){
        loans.setMobileNumber(loansDTO.getMobileNumber());
        loans.setLoanNumber(loansDTO.getLoanNumber());
        loans.setLoanType(loansDTO.getLoanType());
        loans.setTotalLoan(loansDTO.getTotalLoan());
        loans.setAmountPaid(loansDTO.getAmountPaid());
        loans.setOutstandingAmount(loansDTO.getOutstandingAmount());
        return  loans;
    }
}
