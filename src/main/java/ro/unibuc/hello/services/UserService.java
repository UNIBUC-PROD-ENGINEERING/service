package ro.unibuc.hello.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.controllers.contracts.UserCreateRequest;
import ro.unibuc.hello.dtos.UserDTO;
import ro.unibuc.hello.entities.Policy;
import ro.unibuc.hello.entities.User;
import ro.unibuc.hello.exceptions.EntityAlreadyExistsException;
import ro.unibuc.hello.exceptions.EntityNotFoundException;
import ro.unibuc.hello.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PolicyService policyService;

    public UserDTO getUserById(String id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User")
        ).toDTO();
    }

    public UserDTO addUser(UserCreateRequest userCreateRequest) {
        var existingUser = userRepository.findById(userCreateRequest.getId());
        if (existingUser.isPresent()) {
            throw new EntityAlreadyExistsException("User");
        }
        List<Policy> policies = new ArrayList<>();
        userCreateRequest.getPolicies().forEach(policyId -> {
            var policy = policyService.getPolicyById(policyId);
            policies.add(policy.toPolicy());
        });
        var user = new User(userCreateRequest.getId(), policies);
        userRepository.save(user);
        return user.toDTO();
    }

    public UserDTO updateUser(String id, UserCreateRequest userCreateRequest) {
        var existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            throw new EntityAlreadyExistsException("User");
        }
        List<Policy> policies = new ArrayList<>();
        userCreateRequest.getPolicies().forEach(policyId -> {
            var policy = policyService.getPolicyById(policyId);
            policies.add(policy.toPolicy());
        });
        var user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User")
        );
        user.setPolicies(policies);
        userRepository.save(user);
        return user.toDTO();
    }

    public void deleteUser(String id) {
        var user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User")
        );
        userRepository.delete(user);
    }

    public List<UserDTO> getUsers() {
        var users = userRepository.findAll();
        return users.stream().map(User::toDTO).collect(Collectors.toList());
    }

    public UserDTO addPoliciesToUser(String userId, List<String> policyIds) {
        var user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User")
        );
        List<Policy> policies = new ArrayList<>();
        policyIds.forEach(policy -> {
            var existingPolicy = policyService.getPolicyById(policy);
            policies.add(existingPolicy.toPolicy());
        });
        user.getPolicies().addAll(policies);
        userRepository.save(user);
        return user.toDTO();
    }
}
