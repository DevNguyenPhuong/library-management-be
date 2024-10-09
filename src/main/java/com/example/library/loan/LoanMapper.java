package com.example.library.loan;

import com.example.library.dto.loan.LoanRequest;
import com.example.library.dto.loan.LoanResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LoanMapper {
    @Mapping(target = "id", ignore = true)
    Loan toLoan(LoanRequest loanRequest);


    LoanResponse toLoanResponse(Loan loan);

    @Mapping(target="patronId", ignore = true)
    @Mapping(target="bookCopyId", ignore = true)
    @Mapping(target="userId", ignore = true)
    @Mapping(target="id", ignore = true)
    void updateLoan(@MappingTarget Loan loan, LoanRequest loanRequest);
}
