package ru.merenkov.infoSysWeb.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.merenkov.infoSysWeb.student.StudentService;

@Controller
@RequestMapping(path = "group")
public class GroupController {

    private final GroupService groupService;
    private final StudentService studentService;

    @Autowired
    public GroupController(GroupService groupService, StudentService studentService) {
        this.groupService = groupService;
        this.studentService = studentService;
    }

    @GetMapping
    public String getGroups(Model model) {
        model.addAttribute("groups", groupService.getGroups());
        return "groups";
    }

    @GetMapping(path = "/search")
    public String getSearchedStudents(Model model, @RequestParam("needle") String needle) {
        model.addAttribute("groups", groupService.searchGroups(needle));
        return "groups";
    }

    @GetMapping(path = "/sort")
    public String getSortedStudents(Model model, @RequestParam("sort") String sort) {
        model.addAttribute("groups", groupService.getGroupsSortedBy(sort));
        return "groups";
    }

    @GetMapping(path = "{groupId}")
    public String getGroup(Model model, @PathVariable("groupId") Long groupId) {
        model.addAttribute("selectedGroup", groupService.getGroupById(groupId));
        return "selectedGroupInfo";
    }

    @GetMapping(path = "{groupId}/update")
    public String updateGroup(Model model, @PathVariable("groupId") Long groupId) {
        model.addAttribute("groupId", groupId);
        model.addAttribute("group", new Group());
        return "updateGroup";
    }

    @PostMapping(path = "{groupId}/update")
    public String addUpdatedGroup(@ModelAttribute("group") Group group, @PathVariable("groupId") Long groupId) {
        groupService.updateGroup(groupId, group);
        return "redirect:/group";
    }

    @GetMapping("/new")
    public String createGroup(@ModelAttribute("group") Group group) {
        return "newGroup";
    }

    @PostMapping
    public String registerNewGroup(@ModelAttribute("group") Group group) {
        groupService.addNewGroup(group);
        return "redirect:/group";
    }

    @GetMapping(path = "{groupId}/delete")
    public String deleteGroup(@PathVariable("groupId") Long groupId) {
        if(groupService.deleteGroup(groupId, studentService.getStudents())) {
            return "redirect:/group";
        } else {
            return "studentDeleteError";
        }

    }
}
