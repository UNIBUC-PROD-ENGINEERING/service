package ro.unibuc.hello.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import ro.unibuc.hello.data.BookEntity;
import ro.unibuc.hello.data.LoanEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LoanControllerUnitTest {
    LoanController loanController = new LoanController();
    @Test
    void getLoanByIdSuccessfully() {
        List<LoanEntity> loanList = loanController.getAllLoans();
        LoanEntity loan = loanList.get(0);

        //Act
        LoanEntity readLoan = loanController.getLoanById(loan.getId());

        //Assert
        Assertions.assertThrows(RuntimeException.class, () -> {
            loanController.getLoanById(loan.getId());
        });
    }
}