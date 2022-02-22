package ru.merenkov.infoSysWeb.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.merenkov.infoSysWeb.group.GroupService;

@Controller
@RequestMapping(path = "student")
public class StudentController {

    private final StudentService studentService;
    private final GroupService groupService;

    @Autowired
    public StudentController(StudentService studentService, GroupService groupService) {
        this.studentService = studentService;
        this.groupService = groupService;
    }

    @GetMapping
    public String getStudents(Model model) {
        model.addAttribute("students", studentService.getStudents());
        return "students";
    }

    @GetMapping(path = "/search")
    public String getSearchedStudents(Model model, @RequestParam("needle") String needle) {
        model.addAttribute("students", studentService.searchStudents(needle));
        return "students";
    }

    @GetMapping(path = "/sort")
    public String getSortedStudents(Model model, @RequestParam("sort") String sort) {
        model.addAttribute("students", studentService.getStudentsSortedBy(sort));
        return "students";
    }

    @GetMapping(path = "{studentId}")
    public String getStudent(Model model, @PathVariable("studentId") Long studentId) {
        model.addAttribute("selectedStudent", studentService.getStudentById(studentId));
        return "selectedStudentInfo";
    }

    @GetMapping(path = "{studentId}/update")
    public String updateStudent(Model model, @PathVariable("studentId") Long studentId) {
        model.addAttribute("student", new Student());
        model.addAttribute("groups", groupService.getGroups());

        model.addAttribute("studentId", studentId);
        return "updateStudent";
    }

    @PostMapping(path = "{studentId}/update")
    public String addUpdatedStudent(@ModelAttribute("student") Student student, @PathVariable("studentId") Long studentId,
                                    @ModelAttribute("groupId") Long groupId) {
        student.setStudentGroup(groupService.getGroupRepos().getById(groupId));
        studentService.updateStudent(studentId, student);
        return "redirect:/student";
    }

    @GetMapping("/new")
    public String createNewStudent(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("groups", groupService.getGroups());
        return "newStudent";
    }

    @PostMapping
    public String registerNewStudent(@ModelAttribute("student") Student student, @RequestParam("groupId") Long groupId) {
        student.setStudentGroup(groupService.getGroupById(groupId));
        studentService.addNewStudent(student);
        return "redirect:/student";
    }

    @GetMapping(path = "{studentId}/delete")
    public String deleteStudent(@PathVariable("studentId") Long studentId) {
        studentService.deleteStudent(studentId);
        return "redirect:/student";
    }
}
