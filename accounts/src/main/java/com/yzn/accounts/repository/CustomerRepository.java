package com.yzn.accounts.repository;

import com.yzn.accounts.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // Derived named method
    Optional<Customer> findByMobileNumber(String mobileNumber);
}
