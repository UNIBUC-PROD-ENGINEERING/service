package ro.unibuc.hello.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.controllers.contracts.PolicyCreateRequest;
import ro.unibuc.hello.dtos.PolicyDTO;
import ro.unibuc.hello.exceptions.EntityNotFoundException;
import ro.unibuc.hello.services.PolicyService;

import java.util.List;

@Controller
public class PolicyController {

    @Autowired
    private PolicyService policyService;

    @GetMapping("/policies")
    @ResponseBody
    public List<PolicyDTO> getPolicies() {
        return policyService.getPolicies();
    }

    @GetMapping("/policies/{id}")
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    public PolicyDTO getPolicyById(@PathVariable String id) {
        return policyService.getPolicyById(id);
    }

    @PostMapping("/policies")
    @ResponseBody
    public PolicyDTO addPolicy(@RequestBody PolicyCreateRequest policy) {
        return policyService.addPolicy(policy.getName(), policy.getStatements());
    }

    @PutMapping("/policies/{id}")
    @ResponseBody
    public PolicyDTO updatePolicy(@PathVariable String id, @RequestBody PolicyCreateRequest policy) {
        return policyService.updatePolicy(id, policy.getName(), policy.getStatements());
    }

    @DeleteMapping("/policies/{id}")
    @ResponseBody
    public void deletePolicy(@PathVariable String id) {
        policyService.deletePolicy(id);
    }

}
