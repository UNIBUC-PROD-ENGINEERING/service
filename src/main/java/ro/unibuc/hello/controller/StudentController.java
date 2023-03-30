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
    public Student createStudent(@RequestParam(name="name") String name, @RequestParam(name="email") String email, @RequestParam(name="age") int age, @RequestParam(name="grades") double[] grades) {
        return studentRepository.save(new Student(name, email, age, grades));}


    @PutMapping("/student/edit")
    @ResponseBody
    public Student editStudent(@RequestParam(name="id") String id, @RequestParam(name="name") String name, @RequestParam(name="email") String email, @RequestParam(name="age") int age) {
        Student student = studentRepository.findById(String.valueOf(new ObjectId(id))).orElse(null);
        if(student != null) {
            if(name != null)
                student.setName(name);
            if(email != null)
                student.setEmail(email);
            if(age>0)
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
    
    @GetMapping("/student/get")
    @ResponseBody
    public Student getStudent(@RequestParam(name="id") String id) {
        return studentRepository.findById(String.valueOf(new ObjectId(id))).orElse(null);
    }
    @GetMapping("/student/getAll")
    @ResponseBody
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/student/sortByAge")
    @ResponseBody
    public List<Student> sortByAge() {
        List<Student> students = studentRepository.findAll();
        Collections.sort(students, new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                return Integer.compare(s1.getAge(), s2.getAge());
            }
        });
        return students;
    }

    @GetMapping("/student/sortByAverageGrade")
    @ResponseBody
    public List<Student> sortByAverageGrade() {
        List<Student> students = studentRepository.findAll();
        Collections.sort(students, new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                return Double.compare(s1.getAverageGrade(), s2.getAverageGrade());
            }
        });
        return students;
    }


    @GetMapping("/student/sortAlfabetic")
    @ResponseBody
    public List<Student> sortAlfabetic() {
        List<Student> students = studentRepository.findAll();
        Collections.sort(students, new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                return s1.getName().compareTo(s2.getName());
            }
        });
        return students;
    }

}