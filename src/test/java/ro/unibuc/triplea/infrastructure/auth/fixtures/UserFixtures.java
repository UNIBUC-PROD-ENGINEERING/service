package ro.unibuc.triplea.infrastructure.auth.fixtures;

import ro.unibuc.triplea.domain.auth.model.entity.meta.User;
import ro.unibuc.triplea.domain.auth.model.enums.Role;

public class UserFixtures {

    public static User user(String username) {
        return User.builder()
                .username(username)
                .build();
    }

    public static User userWithRoleUser(String username) {
        return User.builder()
                .username(username)
                .role(Role.USER)
                .build();
    }
}
