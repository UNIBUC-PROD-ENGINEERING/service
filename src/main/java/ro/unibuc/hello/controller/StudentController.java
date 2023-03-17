package ro.unibuc.hello.controller;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.unibuc.hello.data.Student;
import ro.unibuc.hello.data.StudentRepository;

import java.util.*;

@Controller
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;

    @DeleteMapping("/student/delete")
    @ResponseBody
    public void deleteStudent(@RequestParam(name="id") String id) {
        studentRepository.deleteById(String.valueOf(new ObjectId(id)));
    }
}