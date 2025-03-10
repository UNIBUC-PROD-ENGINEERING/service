package main.java.ro.unibuc.hello.model;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User {
    @Id
    @SequenceGenerator(
            name="user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "user_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private int id;
    private String email;
    private String last_name;
    private String first_name;
    private String password;
}
