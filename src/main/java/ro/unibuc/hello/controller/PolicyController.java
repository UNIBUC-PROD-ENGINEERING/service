package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.controller.contracts.PolicyCreateRequest;
import ro.unibuc.hello.dto.Policy;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.PolicyService;

import java.util.List;

@Controller
public class PolicyController {

    @Autowired
    private PolicyService policyService;

    @GetMapping("/policies")
    @ResponseBody
    public List<Policy> getPolicies() {
        return policyService.getPolicies();
    }

    @GetMapping("/policies/{id}")
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    public Policy getPolicyById(@PathVariable String id) {
        return policyService.getPolicyById(id);
    }

    @PostMapping("/policies/")
    @ResponseBody
    public Policy addPolicy(@RequestBody Policy policy) {
        return policyService.addPolicy(policy.getName(), policy.getStatements());
    }

    @PutMapping("/policies/{id}")
    @ResponseBody
    public Policy updatePolicy(@PathVariable String id, @RequestBody PolicyCreateRequest policy) {
        return policyService.updatePolicy(id, policy.name(), policy.statements());
    }

    @DeleteMapping("/policies/{id}")
    @ResponseBody
    public void deletePolicy(@PathVariable String id) {
        policyService.deletePolicy(id);
    }

}
