package com.yzn.loans.repository;

import com.yzn.loans.model.Loans;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loans, Integer> {

    Optional<Loans> findByMobileNumber(String mobileNumber);


    Optional<Loans> findByLoanNumber(String loanNumber);

}
