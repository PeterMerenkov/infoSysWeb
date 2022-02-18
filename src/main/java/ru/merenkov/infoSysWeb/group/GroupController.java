package ru.merenkov.infoSysWeb.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.merenkov.infoSysWeb.student.Student;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping(path = "group")
public class GroupController {

    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public String getGroups(Model model) {
        model.addAttribute("groups", groupService.getGroups());
        return "stud-groups";
    }

    /*@PutMapping(path = "{groupId}")
    public void updateGroup(
            @PathVariable("groupId") Long groupId,
            @RequestParam(required = false) Integer number,
            @RequestParam(required = false) String fac) {

        groupService.updateGroup(groupId, number, fac);
    }*/

    @GetMapping("/new")
    public String createGroup(@ModelAttribute("group") Group group) {
        return "newGroup";
    }

    @PostMapping
    public String registerNewGroup(@ModelAttribute("group") Group group) {
        groupService.addNewGroup(group);
        return "redirect:/group";
    }

    /*@DeleteMapping(path = "{groupId}")
    public void deleteGroup(@PathVariable("groupId") Long groupId) {
        groupService.deleteGroup(groupId);
    }*/
}
