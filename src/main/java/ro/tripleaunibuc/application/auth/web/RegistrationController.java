package ro.tripleaunibuc.application.auth.web;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.tripleaunibuc.application.auth.dto.request.RegistrationModel;
import ro.tripleaunibuc.application.auth.dto.response.WelcomeModel;
import ro.tripleaunibuc.domain.auth.service.UserService;
import ro.tripleaunibuc.domain.auth.util.RestConstants;

@RestController
@RequiredArgsConstructor
@Validated
public class RegistrationController {

    private final UserService userService;

    @PostMapping(RestConstants.REGISTER_URL)
    public WelcomeModel registerUser(@RequestBody @Validated RegistrationModel registrationModel) {
        userService.registerUser(registrationModel);
        return new WelcomeModel("Registration is successful");
    }
}
