package ro.unibuc.hello.entity.security;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import ro.unibuc.hello.entity.common.BaseEntity;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class RoleEntity extends BaseEntity {
    @NotEmpty
    @Column(nullable = false, unique = true)
    private String name;
}
