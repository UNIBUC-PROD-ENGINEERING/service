package ro.unibuc.hello.dto;

public class UserDto{
    private String firstName;
    private String lastName;
    private Integer age;
    private String userName;

    public UserDto(String firstName, String lastName, Integer age, String userName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.userName = userName;
    }

    public UserDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        UserDto other = (UserDto) obj;
        return (firstName == null ? other.firstName == null : firstName.equals(other.firstName))
                && (lastName == null ? other.lastName == null : lastName.equals(other.lastName))
                && (age == null ? other.age == null : age.equals(other.age))
                && (userName == null ? other.userName == null : userName.equals(other.userName));
    }
}