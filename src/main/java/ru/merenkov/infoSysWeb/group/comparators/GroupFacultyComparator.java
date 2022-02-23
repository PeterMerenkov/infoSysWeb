package ru.merenkov.infoSysWeb.group.comparators;

import ru.merenkov.infoSysWeb.group.Group;

import java.util.Comparator;
import java.util.Locale;

public class GroupFacultyComparator implements Comparator<Group> {

    @Override
    public int compare(Group group1, Group group2) {
        return group1.getFaculty().toLowerCase(Locale.ROOT).compareTo(group2.getFaculty().toLowerCase(Locale.ROOT));
    }
}
