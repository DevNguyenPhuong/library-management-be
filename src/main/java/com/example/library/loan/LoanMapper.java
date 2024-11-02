package com.example.library.loan;

import com.example.library.bookCopy.BookCopyMapper;
import com.example.library.dto.loan.LoanRequest;
import com.example.library.dto.loan.LoanResponse;
import com.example.library.dto.patron.PatronSimpleResponse;
import com.example.library.dto.publisher.PublisherSimpleResponse;
import com.example.library.dto.user.UserSimpleResponse;
import com.example.library.patron.Patron;
import com.example.library.publisher.Publisher;
import com.example.library.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {BookCopyMapper.class})
public interface LoanMapper {
    @Mapping(target = "id", ignore = true)
    Loan toLoan(LoanRequest loanRequest);


    @Mapping(target = "bookCopy", source = "loan.bookCopy")
    @Mapping(target = "patron", qualifiedByName = "toPatronSimpleResponse")
    @Mapping(target = "user", qualifiedByName = "toUserSimpleResponse")
    LoanResponse toLoanResponse(Loan loan);

    @Mapping(target = "bookId", ignore = true)
    @Mapping(target="bookCopy", ignore = true)
    @Mapping(target="user", ignore = true)
    @Mapping(target="patron", ignore = true)
    @Mapping(target = "fine", ignore = true)
    @Mapping(target="id", ignore = true)
    void updateLoan(@MappingTarget Loan loan, LoanRequest loanRequest);

    @Named("toPatronSimpleResponse")
    default PatronSimpleResponse toPatronSimpleResponse(Patron patron) {
        return new PatronSimpleResponse(patron.getId(), patron.getName());
    }

    @Named("toUserSimpleResponse")
    default UserSimpleResponse toUserSimpleResponse(User user) {
        return new UserSimpleResponse(user.getId(), user.getName());
    }
}
