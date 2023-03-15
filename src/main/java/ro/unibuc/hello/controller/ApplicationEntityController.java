package ro.unibuc.hello.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.unibuc.hello.entity.ApplicationEntity;
import ro.unibuc.hello.service.ApplicationService;

import java.util.List;

@RestController
@RequestMapping("/applications")
public class ApplicationEntityController {
    @Autowired
    private ApplicationService applicationService;

    @GetMapping("/get")
    public List<ApplicationEntity> getAllApplications() {
        return applicationService.getApplications();
    }
}