package ro.unibuc.hello.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController implements ErrorController{
    private final String PATH = "/error";

    @RequestMapping(PATH)
    @ResponseBody
    public String getErrorPath(){
        return "No mapping found.";
    }
}
