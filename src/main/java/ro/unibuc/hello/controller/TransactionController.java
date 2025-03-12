package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ro.unibuc.hello.data.transaction.TransactionDTO;
import ro.unibuc.hello.data.transaction.TransactionEntity;
import ro.unibuc.hello.service.TransactionService;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/api/transactions")
    @ResponseBody
    public TransactionEntity createTransaction(@RequestBody TransactionDTO transactionDTO) {
        try {
            return transactionService.createTransaction(transactionDTO);
        } catch (Exception e) {
            if (e.getMessage().equals(HttpStatus.NOT_FOUND.toString())) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product or user not found");
            } else if (e.getMessage().equals(HttpStatus.BAD_REQUEST.toString())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient stock");
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Service not available");
            }
        }
    }

    @GetMapping("/api/transactions/{id}")
    @ResponseBody
    public TransactionEntity getTransactionById(@PathVariable String id) {
        try {
            return transactionService.getTransactionById(id);
        } catch (Exception e) {
            if (e.getMessage().equals(HttpStatus.NOT_FOUND.toString())) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found");
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Service not available");
            }
        }
    }

    @GetMapping("/api/transactions")
    @ResponseBody
    public List<TransactionEntity> getAllTransactions() {
        try {
            return transactionService.getAllTransactions();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Service not available");
        }
    }

    @GetMapping("/api/transactions/user/{userId}")
    @ResponseBody
    public List<TransactionEntity> getTransactionsByUser(@PathVariable String userId) {
        try {
            return transactionService.getTransactionsByUser(userId);
        } catch (Exception e) {
            if (e.getMessage().equals(HttpStatus.NOT_FOUND.toString())) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Service not available");
            }
        }
    }

    @GetMapping("/api/transactions/range")
    @ResponseBody
    public List<TransactionEntity> getTransactionsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        try {
            return transactionService.getTransactionsByDateRange(startDate, endDate);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Service not available");
        }
    }

    @DeleteMapping("/api/transactions/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTransaction(@PathVariable String id) {
        try {
            transactionService.deleteTransaction(id);
        } catch (Exception e) {
            if (e.getMessage().equals(HttpStatus.NOT_FOUND.toString())) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found");
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Service not available");
            }
        }
    }
}