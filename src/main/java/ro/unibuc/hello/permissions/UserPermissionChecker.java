package ro.unibuc.hello.permissions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.exception.UnauthorizedException;

@Component
public class UserPermissionChecker implements PermissionChecker<UserEntity> {
    @Autowired
    public UserRepository userRepository;

    @Override
    public void checkOwnership(String userId, String resourceId) {
        UserEntity user = userRepository.findById(resourceId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        
        if (!user.getId().equals(userId)) {
            throw new UnauthorizedException("You can't change this user");
        }
    }
}
