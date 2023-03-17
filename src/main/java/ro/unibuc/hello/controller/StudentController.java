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


    @PostMapping("/student/create")
    @ResponseBody
    public Student createStudent(@RequestParam(name="name") String name, @RequestParam(name="email") String email, @RequestParam(name="age") int age) {
        return studentRepository.save(new Student(name, email, age));}


    @PutMapping("/student/edit")
    @ResponseBody
    public Student editStudent(@RequestParam(name="id") String id, @RequestParam(name="name") String name, @RequestParam(name="email") String email, @RequestParam(name="age") int age) {
        Student student = studentRepository.findById(String.valueOf(new ObjectId(id))).orElse(null);
        if(student != null) {
            if(name != null)
                student.setName(name);
            if(email != null)
                student.setEmail(email);
            if(age)
                student.setAge(age);
            return studentRepository.save(student);
        } else
            return  null;
    }


    @DeleteMapping("/student/delete")
    @ResponseBody
    public void deleteStudent(@RequestParam(name="id") String id) {
        studentRepository.deleteById(String.valueOf(new ObjectId(id)));

    }

}