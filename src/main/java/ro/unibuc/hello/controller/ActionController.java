package ro.unibuc.hello.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.ActionEntity;
import ro.unibuc.hello.dto.Action;
import ro.unibuc.hello.exception.EntityAlreadyExistsException;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.ActionService;

import java.util.List;

@Controller
public class ActionController {

    @Autowired
    private ActionService actionService;

    @Operation(summary = "Create an action")
    @PostMapping("/actions/")
    @ExceptionHandler(EntityAlreadyExistsException.class)
    @ResponseBody
    public Action createAction(@RequestBody Action action) {
        return actionService.addAction(action.getCode(), action.getDescription());
    }

    @GetMapping("/actions/")
    @ResponseBody
    public List<Action> seeActions(){
        return actionService.getActions();
    }

    @GetMapping("/actions/{code}")
    @ResponseBody
    @ExceptionHandler(EntityNotFoundException.class)
    public Action seeAction(@PathVariable String code){
        return actionService.getActionById(code);
    }
}
