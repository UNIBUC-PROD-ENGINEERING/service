package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.unibuc.hello.auth.PublicEndpoint;
import ro.unibuc.hello.dto.LoginRequest;
import ro.unibuc.hello.dto.Session;
import ro.unibuc.hello.service.SessionService;


@Controller
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @PublicEndpoint
    @PostMapping("/login")
    @ResponseBody
    public Session getMethodName(@RequestBody LoginRequest loginReq) {
        return sessionService.login(loginReq);
    }

    @PostMapping("/logout")
    @ResponseBody
    public ResponseEntity<String> getMethodName() {
        return sessionService.logout();
    }
}
