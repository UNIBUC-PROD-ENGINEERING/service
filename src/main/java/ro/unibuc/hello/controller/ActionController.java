package ro.unibuc.hello.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.unibuc.hello.data.ActionEntity;
import ro.unibuc.hello.service.ActionService;

@Controller
public class ActionController {


    @Autowired
    private ActionService actionService;

    @PostMapping("/create-action")
    //@PreAuthorize("permitAll()")
    public ActionEntity createAction(@RequestBody ActionEntity action) {
        return actionService.addAction(action.actionCode, action.actionDescription);
    }

    @GetMapping("/see-all-actions")
    @ResponseBody
    public List<ActionEntity> seeActions(){
        return actionService.getActions();
    }

    @GetMapping("/action")
    @ResponseBody
    public ActionEntity getAction(@RequestParam(name="code") String code) {
        return actionService.getActionById(code);
    }
}
