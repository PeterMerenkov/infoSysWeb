package ru.merenkov.infoSysWeb.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.merenkov.infoSysWeb.student.Student;

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
        groupService.deleteGroup(groupId);
        return "redirect:/group";
    }
}
