package ru.merenkov.infoSysWeb.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.merenkov.infoSysWeb.group.GroupId;
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
    public String createNewStudent(Model model) {
        model.addAttribute("groupId", new GroupId());
        model.addAttribute("student", new Student());
        model.addAttribute("groups", groupService.getGroups());
        return "new";
    }

    @PostMapping
    public String registerNewStudent(@ModelAttribute("student") Student student, @ModelAttribute("groupId") GroupId groupId) {
        student.setStudentGroup(groupService.getGroupRepos().getById(groupId.getGroupId()));
        studentService.addNewStudent(student);
        return "redirect:/student";
    }

    /*@DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long studentId) {
        studentService.deleteStudent(studentId);
    }*/
}
