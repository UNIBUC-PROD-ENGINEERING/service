
package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;

import java.util.Random;

public class UserEntity {
    @Id
    private long id = new Random().nextLong();
    private String firstName;
    private String lastName;
    private String email;

    public UserEntity() {
    }

    public UserEntity(long id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }


