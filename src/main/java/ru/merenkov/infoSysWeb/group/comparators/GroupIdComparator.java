package ru.merenkov.infoSysWeb.group.comparators;

import ru.merenkov.infoSysWeb.group.Group;

import java.util.Comparator;

public class GroupIdComparator implements Comparator<Group> {

    @Override
    public int compare(Group group1, Group group2) {
        return group1.getId().compareTo(group2.getId());
    }
}
