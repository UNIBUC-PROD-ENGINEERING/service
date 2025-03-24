package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.entity.MoneyRequest;
import ro.unibuc.hello.service.MoneyRequestService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/money-requests")
public class MoneyRequestController {

    private final MoneyRequestService moneyRequestService;

    @Autowired
    public MoneyRequestController(MoneyRequestService moneyRequestService) {
        this.moneyRequestService = moneyRequestService;
    }

    @GetMapping
    public List<MoneyRequest> getAllRequests() {
        return moneyRequestService.getAllRequests();
    }

    @GetMapping("/{id}")
    public Optional<MoneyRequest> getRequestById(@PathVariable String id) {
        return moneyRequestService.getRequestById(id);
    }

    @GetMapping("/user/{toAccountId}")
    public List<MoneyRequest> getRequestsForUser(@PathVariable String toAccountId) {
        return moneyRequestService.getRequestsForUser(toAccountId);
    }

    @PostMapping
    public MoneyRequest createRequest(@RequestBody MoneyRequest request) {
        return moneyRequestService.createRequest(request);
    }

    @PutMapping("/{id}/status")
    public MoneyRequest updateRequestStatus(@PathVariable String id, @RequestParam String status) {
        if (!status.equals("APPROVED") && !status.equals("DECLINED")) {
            throw new IllegalArgumentException("Bank account not found.");
        }
        return moneyRequestService.updateRequestStatus(id, status);
    }

}
