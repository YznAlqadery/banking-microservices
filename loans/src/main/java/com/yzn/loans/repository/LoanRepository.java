package com.yzn.loans.repository;

import com.yzn.loans.model.Loans;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loans, Integer> {
}
