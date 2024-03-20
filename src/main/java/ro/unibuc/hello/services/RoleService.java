package ro.unibuc.hello.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.unibuc.hello.controllers.contracts.RoleCreateRequest;
import ro.unibuc.hello.dtos.RoleDTO;
import ro.unibuc.hello.entities.Policy;
import ro.unibuc.hello.entities.Role;
import ro.unibuc.hello.exceptions.EntityAlreadyExistsException;
import ro.unibuc.hello.exceptions.EntityNotFoundException;
import ro.unibuc.hello.repositories.RoleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PolicyService policyService;

    public List<RoleDTO> getRoles() {
        var roles = roleRepository.findAll();
        return roles.stream().map(Role::toDTO).collect(Collectors.toList());
    }

    public RoleDTO getRoleById(String id){
        return roleRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Role")
        ).toDTO();
    }

    public RoleDTO addRole(RoleCreateRequest roleCreateRequest) {
        var existingRole = roleRepository.findById(roleCreateRequest.getId());
        if (existingRole.isPresent()) {
            throw new EntityAlreadyExistsException("Role");
        }
        List<Policy> policies = new ArrayList<>();
        roleCreateRequest.getPolicies().forEach(policyId -> {
            var policy = policyService.getPolicyById(policyId);
            policies.add(policy.toPolicy());
        });
        var role = new Role(roleCreateRequest.getId(), policies);
        roleRepository.save(role);
        return role.toDTO();
    }

    public RoleDTO updateRole(String id, RoleCreateRequest roleCreateRequest) {
        var existingRole = roleRepository.findById(id);
        if (existingRole.isEmpty()) {
            throw new EntityNotFoundException("Role");
        }
        List<Policy> policies = new ArrayList<>();
        roleCreateRequest.getPolicies().forEach(policyId -> {
            var policy = policyService.getPolicyById(policyId);
            policies.add(policy.toPolicy());
        });
        var role = roleRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Role")
        );
        role.setPolicies(policies);
        roleRepository.save(role);
        return role.toDTO();
    }

    public void deleteRole(String id) {
        var role = roleRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Role")
        );
        roleRepository.delete(role);
    }
}
