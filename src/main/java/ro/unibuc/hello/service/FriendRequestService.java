package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.unibuc.hello.data.FriendRequestEntity;
import ro.unibuc.hello.data.FriendRequestRepository;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.dto.FriendRequest;
import ro.unibuc.hello.dto.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import ro.unibuc.hello.exception.EntityNotFoundException;


@Service
public class FriendRequestService {

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @Autowired
    private UserRepository userRepository;


    public FriendRequestEntity sendRequest(String fromUserId, String toUserId) {
        if (fromUserId.equals(toUserId)) {
            throw new IllegalArgumentException("You cannot send a friend request to yourself.");
        }
    
        boolean alreadyExists = friendRequestRepository.findByFromUserIdAndToUserId(fromUserId, toUserId)
                .stream()
                .anyMatch(req -> req.getStatus().equals("PENDING") || req.getStatus().equals("ACCEPTED"));
    
        alreadyExists |= friendRequestRepository.findByFromUserIdAndToUserId(toUserId, fromUserId)
                .stream()
                .anyMatch(req -> req.getStatus().equals("PENDING") || req.getStatus().equals("ACCEPTED"));
    
        if (alreadyExists) {
            throw new IllegalArgumentException("Friend request already exists or you are already friends.");
        }
    
        FriendRequestEntity request = new FriendRequestEntity(fromUserId, toUserId, "PENDING");
        return friendRequestRepository.save(request);
    }
    

    public List<FriendRequestEntity> getReceivedRequests(String toUserId) {
        return friendRequestRepository.findByToUserIdAndStatus(toUserId, "PENDING");
    }

    public boolean respondToRequest(String requestId, boolean accept) {
        Optional<FriendRequestEntity> optional = friendRequestRepository.findById(requestId);
        if (optional.isPresent()) {
            FriendRequestEntity request = optional.get();
    
            if (!"PENDING".equals(request.getStatus())) {
                return false; 
            }
    
            request.setStatus(accept ? "ACCEPTED" : "REJECTED");
            friendRequestRepository.save(request);
            return true;
        }
        return false;
    }
    

    private FriendRequest convertToDto(FriendRequestEntity entity) {
        return new FriendRequest(
            entity.getId(),
            entity.getFromUserId(),
            entity.getToUserId(),
            entity.getStatus()
        );
    }

    public List<User> getFriendsOfUser(String userId) {
        Optional<UserEntity> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new EntityNotFoundException("User with id " + userId);
        }
    
        List<FriendRequestEntity> acceptedSent = friendRequestRepository.findByFromUserIdAndStatus(userId, "ACCEPTED");
        List<FriendRequestEntity> acceptedReceived = friendRequestRepository.findByToUserIdAndStatus(userId, "ACCEPTED");
    
        List<String> friendIds = new ArrayList<>();
        acceptedSent.forEach(fr -> friendIds.add(fr.getToUserId()));
        acceptedReceived.forEach(fr -> friendIds.add(fr.getFromUserId()));
    
        if (friendIds.isEmpty()) {
            throw new IllegalStateException("You don't have any friends yet.");
        }
    
        List<UserEntity> friends = userRepository.findAllById(friendIds);
        return friends.stream()
                .map(entity -> new User(entity.getId(), entity.getUsername(), entity.getPassword(), entity.getTier(), entity.getExpirationDate()))
                .collect(Collectors.toList());
    }
    

    public boolean removeFriend(String userId1, String userId2) {
        List<FriendRequestEntity> reqs = new ArrayList<>();
        reqs.addAll(friendRequestRepository.findByStatusAndFromUserIdAndToUserId("ACCEPTED", userId1, userId2));
        reqs.addAll(friendRequestRepository.findByStatusAndFromUserIdAndToUserId("ACCEPTED", userId2, userId1));
    
        if (reqs.isEmpty()) {
            return false; 
        }
    
        friendRequestRepository.deleteAll(reqs);
        return true;
    }
    

}

