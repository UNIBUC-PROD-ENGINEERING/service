package ro.unibuc.triplea.infrastructure.auth;

import ro.unibuc.triplea.domain.auth.model.entity.meta.User;

public class UserFixtures {

    public static User user(String username) {
        return User.builder()
                .username(username)
                .build();
    }
}
