package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.entity.Group;
import ro.unibuc.hello.repository.GroupRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    public Optional<Group> getGroupById(String id) {
        return groupRepository.findById(id);
    }

    public Group saveGroup(Group group) {
        return groupRepository.save(group);
    }

    public void deleteGroup(String id) {
        groupRepository.deleteById(id);
    }

    public Group addClientToGroup(String groupId, String clientId) {
        Optional<Group> optionalGroup = groupRepository.findById(groupId);
        if (optionalGroup.isPresent()) {
            Group group = optionalGroup.get();
            if (!group.getClientIds().contains(clientId)) {
                group.getClientIds().add(clientId);
                groupRepository.save(group);
            }
            return group;
        }
        return null;
    }

    public Group addTransactionToGroup(String groupId, String transactionId) {
        Optional<Group> optionalGroup = groupRepository.findById(groupId);
        if (optionalGroup.isPresent()) {
            Group group = optionalGroup.get();
            if (!group.getTransactionIds().contains(transactionId)) {
                group.getTransactionIds().add(transactionId);
                groupRepository.save(group);
            }
            return group;
        }
        return null;
    }
}
