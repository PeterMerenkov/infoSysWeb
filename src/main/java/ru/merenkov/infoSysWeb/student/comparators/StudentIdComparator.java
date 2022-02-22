package ru.merenkov.infoSysWeb.student.comparators;

import ru.merenkov.infoSysWeb.student.Student;

import java.util.Comparator;

public class StudentIdComparator implements Comparator<Student> {

    @Override
    public int compare(Student student1, Student student2) {
        return student1.getId().compareTo(student2.getId());
    }
}
