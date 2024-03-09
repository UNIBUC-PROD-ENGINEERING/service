package ro.tripleaunibuc.application.auth.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AngularRouteController {

    @GetMapping(value = {
            "/login",
            "/logout",
            "/register",
            "/welcome", "/welcome/*",
            "/todos", "/todos/*"
    })
    public String frontend() {
        return "forward:/";
    }
}
