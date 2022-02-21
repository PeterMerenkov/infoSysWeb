package ru.merenkov.infoSysWeb.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class GroupService {

    private final GroupRepos groupRepos;

    @Autowired
    public GroupService(GroupRepos groupRepos) {
        this.groupRepos = groupRepos;
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

    public void deleteGroup(Long groupId) {
        boolean exists = groupRepos.existsById(groupId);

        if (!exists) {
            throw new IllegalStateException(
                    "group w id: " + groupId + " doesn't exist!"
            );
        }
        groupRepos.deleteById(groupId);
    }
}
