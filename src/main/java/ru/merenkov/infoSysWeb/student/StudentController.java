package ru.merenkov.infoSysWeb.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping(path = "student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public String getStudents(Model model) {
        model.addAttribute("students", studentService.getStudents());
        return "index";
    }

    /*@PutMapping(path = "{studentId}")
    public void updateStudent(
            @PathVariable("studentId") Long studentId,
            @RequestParam("dateOfAdmission") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateOfAdmission,
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) Long groupId) {

        studentService.updateStudent(studentId, fullName, dateOfAdmission, groupId);
    }*/

    @GetMapping("/new")
    public String createNewStudent(@ModelAttribute("student") Student student) {
        return "new";
    }

    @PostMapping
    public String registerNewStudent(@ModelAttribute("student") Student student) {
        studentService.addNewStudent(student);
        return "redirect:/student";
    }

    /*@DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long studentId) {
        studentService.deleteStudent(studentId);
    }*/
}
