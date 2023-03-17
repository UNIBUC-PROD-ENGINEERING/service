package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ro.unibuc.hello.dto.ApplicationDto;
import ro.unibuc.hello.dto.ProjectDto;
import ro.unibuc.hello.dto.UpdateProjectDto;
import ro.unibuc.hello.entity.ApplicationEntity;
import ro.unibuc.hello.entity.ProjectEntity;
import ro.unibuc.hello.exception.ProjectNotFoundException;
import ro.unibuc.hello.service.ApplicationService;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/applications")
public class ApplicationEntityController {
    @Autowired
    private ApplicationService applicationService;

    @GetMapping("/get-all")
    public List<ApplicationEntity> getAllApplications() {
        return applicationService.getApplications();
    }

    @RequestMapping(method = {RequestMethod.GET}, value="/project/{id}")
    @ResponseBody
    public ResponseEntity<?> getApplicationForProject(@PathVariable("id") String id){
        try {
            List<ApplicationEntity> apps = applicationService.getAppsByProjectId(id);

            return ResponseEntity.ok(apps);
        } catch (RuntimeException exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource Not Found", exc);
        }
    }

    @PostMapping(path = "/apply")
    public ResponseEntity<?> createApplication(@RequestBody ApplicationDto applicationDto) {
        ApplicationEntity app = applicationService.apply(applicationDto);
        return ResponseEntity.ok(app);
    }

    //add exception
    @PutMapping("/accept/{id}")
    public ResponseEntity<?> acceptApplication(@PathVariable("id") String id) {
        applicationService.acceptApplicationById(id);
        String response = "Accepted " + id;
        return ResponseEntity.ok(response);
    }

    //add exception
    @PutMapping("/reject/{id}")
    public ResponseEntity<?> rejectApplication(@PathVariable("id") String id) {
        applicationService.rejectApplicationById(id);
        String response = "Rejected " + id;
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteApplicationById(@PathVariable("id") String id) {
        applicationService.deleteApplicationById(id);
        String response = "Deleted " + id;
        return ResponseEntity.ok(response);
    }

}