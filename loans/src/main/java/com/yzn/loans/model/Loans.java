package com.yzn.loans.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "loans")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Loans extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer loanId;

    private String mobileNumber;

    private String loanNumber;

    private String totalLoan;

    private Integer amountPaid;

    private Integer outstandingAmount;
}
