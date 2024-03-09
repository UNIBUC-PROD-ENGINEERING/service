package ro.tripleaunibuc.application.auth.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ro.tripleaunibuc.application.auth.dto.response.WelcomeModel;
import ro.tripleaunibuc.domain.auth.util.RestConstants;

@RestController
public class WelcomeController {

    @GetMapping(RestConstants.WELCOME_URL)
    public WelcomeModel getWelcomeModel(@PathVariable String name) {
        WelcomeModel welcomeModel = new WelcomeModel();
        welcomeModel.setMessage(String.format("Hello, %s, from rest service", name));
        return welcomeModel;
    }
}
