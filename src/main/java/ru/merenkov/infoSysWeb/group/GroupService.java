package ru.merenkov.infoSysWeb.group;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.merenkov.infoSysWeb.group.comparators.GroupFacultyComparator;
import ru.merenkov.infoSysWeb.group.comparators.GroupIdComparator;
import ru.merenkov.infoSysWeb.group.comparators.GroupNumberComparator;
import ru.merenkov.infoSysWeb.student.Student;

import javax.transaction.Transactional;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.*;

@Service
public class GroupService {

    private final GroupRepos groupRepos;

    @Autowired
    public GroupService(GroupRepos groupRepos) {
        this.groupRepos = groupRepos;
    }

    public boolean existsById(Long id) {
        return groupRepos.existsById(id);
    }

    public GroupRepos getGroupRepos() {
        return groupRepos;
    }

    public List<Group> getGroups() {
        return groupRepos.findAll();
    }

    public Group getGroupById(Long id) {
        return groupRepos.getById(id);
    }

    @Transactional
    public void updateGroup(Long groupId, Group otherGroup) {
        Group group = groupRepos.findById(groupId).orElseThrow(
                () -> new IllegalStateException(
                        "group w id " + groupId + " doesn't exist!"
                ));

        if (otherGroup.getGroupNumber() != null &&
                otherGroup.getGroupNumber() > 0 &&
                !Objects.equals(group.getGroupNumber(), otherGroup.getGroupNumber())) {

            group.setGroupNumber(otherGroup.getGroupNumber());
        }

        if (otherGroup.getFaculty() != null &&
                otherGroup.getFaculty().length() > 0 &&
                !Objects.equals(group.getFaculty(), otherGroup.getFaculty())) {

            group.setFaculty(otherGroup.getFaculty());
        }
    }

    public void addNewGroup(Group group) {
        groupRepos.save(group);
    }

    public boolean deleteGroup(Long groupId, List<Student> students) {
        Group group = groupRepos.getById(groupId);

        boolean canDelete = true;

        for(Student student : students) {
            if (student.getStudentGroup().compareTo(group) == 0) {
                canDelete = false;
                break;
            }
        }

        if (canDelete) {
            groupRepos.deleteById(groupId);
            return true;
        } else {
            return false;
        }
    }

    public List<Group> searchGroups(String needle) {
        List<Group> searchedGroups = new ArrayList<>();

        for (Group group : getGroups()) {
            if (group.getFaculty().toLowerCase(Locale.ROOT).contains(needle.toLowerCase(Locale.ROOT))) {
                searchedGroups.add(group);
            }
        }

        return searchedGroups;
    }

    public List<Group> getGroupsSortedBy(String sort) {

        List<Group> sortedGroups = getGroups();

        switch (sort) {
            case "ById" -> sortedGroups.sort(new GroupIdComparator());
            case "ByFaculty" -> sortedGroups.sort(new GroupFacultyComparator());
            case "ByGroupNumber" -> sortedGroups.sort(new GroupNumberComparator());
        }

        return sortedGroups;
    }
}
