package ro.unibuc.hello.controller;

import com.mongodb.DuplicateKeyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.ResponseStatusException;
import ro.unibuc.hello.dto.UserDto;
import ro.unibuc.hello.entity.ProjectEntity;
import ro.unibuc.hello.entity.UserEntity;
import ro.unibuc.hello.service.UserService;
import ro.unibuc.hello.util.PasswordUtil;

import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping(path = "/register")
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {

        LOGGER.info("RegisterController::TryingToRegisterNewUser");

        try {
            UserEntity user = userService.saveUser(userDto);
            LOGGER.info("RegisterController::UserRegisterSuccess::" + user.getId());
            return ResponseEntity.ok(user);
        } catch (DuplicateKeyException isexc) {
            LOGGER.info("RegisterController::UserRegisterFail::EmailExistent::");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already in use");
        }
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        UserEntity foundUser = userService.getUserByEmail(email);

        boolean matchPassword = PasswordUtil.verifyUserPassword(password, foundUser.getPassword(), foundUser.getEmail());

        if (foundUser != null && matchPassword) {
            return ResponseEntity.ok(foundUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    @RequestMapping(method = {RequestMethod.GET}, value="/{id}")
    @ResponseBody
    public ResponseEntity<?> getUser(@PathVariable("id") String id) {
        try {
            LOGGER.info("UserController::GetById::" + id);

            UserEntity task = userService.getUser(id);

            return ResponseEntity.ok(task);
        } catch (RuntimeException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource Not Found", exc);
        }
    }

    @GetMapping("/get-all")
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }

}

