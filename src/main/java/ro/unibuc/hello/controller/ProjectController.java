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
import ro.unibuc.hello.entity.ProjectEntity;
import ro.unibuc.hello.dto.ProjectDto;
import ro.unibuc.hello.service.ProjectService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);
    @Autowired
    private ProjectService projectService;

   @PostMapping(path = "/add")
    public ResponseEntity<?> createProject(@RequestBody ProjectDto projectDto){
       LOGGER.info("ProjectController: " + projectDto);
       ProjectEntity project = projectService.saveProject(projectDto);

       return ResponseEntity.ok(project);
   }

    @RequestMapping(method = {RequestMethod.GET}, value="/{id}")
    @ResponseBody
    public ResponseEntity<?> getProject(@PathVariable("id") String id){
       try {
           ProjectEntity project = projectService.getProjectById(id);

           return ResponseEntity.ok(project);
       } catch (RuntimeException exc) {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource Not Found", exc);
       }
    }

    @GetMapping("/get-all")
    public List<ProjectEntity> getAllProjects() { return projectService.getAllProjects();}

    @DeleteMapping("/delete/{id}")
    public void deleteProjectById(@PathVariable("id") String id){
       projectService.deleteProjectById(id);
    }
}
