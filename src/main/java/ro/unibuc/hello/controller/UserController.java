package ro.unibuc.hello.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ro.unibuc.hello.dto.UserDto;
import ro.unibuc.hello.entity.UserEntity;
import ro.unibuc.hello.service.UserService;


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

        LOGGER.info("RegisterController: " + userDto);
        UserEntity user = userService.saveUser(userDto);

        return ResponseEntity.ok(user);
    }

    @RequestMapping(method = {RequestMethod.GET}, value="/{id}")
    @ResponseBody
    public ResponseEntity<?> getUser(@PathVariable("id") String id) {
        try {
            LOGGER.info("TasksController::: " + id);

            UserEntity task = userService.getUser(id);

            return ResponseEntity.ok(task);
        } catch (RuntimeException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource Not Found", exc);
        }
    }
}

