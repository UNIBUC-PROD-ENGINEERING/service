package ro.tripleaunibuc.application.auth.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegistrationModel {

    //@Size(min=3, max=50, message = "Min size is 3 and max size is 50")
    private String username;
    //@Size(min=6, max=50, message = "Min size is 3 and max size is 50")
    private String password;

}