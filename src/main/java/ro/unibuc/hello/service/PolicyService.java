package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import ro.unibuc.hello.data.PolicyEntity;
import ro.unibuc.hello.data.PolicyRepository;
import ro.unibuc.hello.dto.Policy;
import ro.unibuc.hello.dto.Statement;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.List;
import java.util.UUID;

@Component
public class PolicyService {

    @Autowired
    private PolicyRepository policyRepository;

    public Policy getPolicyById(String id) throws EntityNotFoundException {
        var policy = policyRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Policy not found.")
        );
        return new Policy(policy.policyId, policy.policyName, policy.statements);
    }

    public Policy addPolicy(String name, List<Statement> statements) {
        var id = UUID.randomUUID().toString();
        var policyEntity = new PolicyEntity(id, name, statements);
        policyRepository.save(policyEntity);
        return new Policy(policyEntity.policyId, policyEntity.policyName, policyEntity.statements);
    }

    public Policy updatePolicy(String id, String name, List<Statement> statements) throws EntityNotFoundException {
        var policyEntity = policyRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Policy not found.")
        );
        policyEntity.policyName = name;
        policyEntity.statements = statements;
        policyRepository.save(policyEntity);
        return new Policy(policyEntity.policyId, policyEntity.policyName, policyEntity.statements);
    }

    public void deletePolicy(String id) throws EntityNotFoundException {
        var policy = policyRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Policy not found.")
        );
        policyRepository.delete(policy);
    }

    public List<Policy> getPolicies() {
        var policies = policyRepository.findAll();
        return policies.stream().map(policy -> new Policy(policy.policyId, policy.policyName, policy.statements)).toList();
    }

}
