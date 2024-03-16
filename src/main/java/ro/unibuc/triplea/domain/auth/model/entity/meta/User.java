package ro.unibuc.triplea.domain.auth.model.entity.meta;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ro.unibuc.triplea.domain.auth.model.enums.Gender;
import ro.unibuc.triplea.domain.auth.model.enums.Role;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Users")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "FirstName", nullable = false, length = 100)
    private String firstname;

    @Column(name = "LastName", nullable = false, length = 100)
    private String lastname;

    @Column(name = "Address", nullable = false, length = 100)
    private String address;

    @Column(name = "mobileNumber", nullable = false, length = 20)
    private String mobileNumber;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String password;

    @Column(name = "Email", nullable = false, length = 100, unique = true)
    private String username;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
