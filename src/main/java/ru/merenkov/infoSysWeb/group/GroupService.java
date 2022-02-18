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

    public List<Group> getGroups() {
        return groupRepos.findAll();
    }

    @Transactional
    public void updateGroup(Long groupId, Integer number, String fac) {
        Group group = groupRepos.findById(groupId).orElseThrow(
                () -> new IllegalStateException(
                        "group w id " + groupId + " doesn't exist!"
                ));

        if (number != null &&
                number > 0 &&
                !Objects.equals(group.getGroupNumber(), number)) {

            group.setGroupNumber(number);
        }

        if (fac != null &&
                fac.length() > 0 &&
                !Objects.equals(group.getFaculty(), fac)) {

            group.setFaculty(fac);
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
