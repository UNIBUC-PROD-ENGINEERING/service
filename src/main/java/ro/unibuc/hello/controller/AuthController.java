package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ro.unibuc.hello.dto.request.LoginDto;
import ro.unibuc.hello.dto.request.RegisterDto;
import ro.unibuc.hello.dto.response.AuthDto;
import ro.unibuc.hello.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public AuthDto login(@RequestBody @Valid LoginDto loginDto) {return authService.login(loginDto);}

    @PostMapping("/register")
    public AuthDto register(@RequestBody @Valid RegisterDto registerDto) {return authService.register(registerDto);}

    @PostMapping("/logout")
    public void logout(){authService.logout();}
}
