package ru.merenkov.infoSysWeb.student.comparators;

import ru.merenkov.infoSysWeb.student.Student;

import java.util.Comparator;
import java.util.Locale;

public class StudentFullNameComparator implements Comparator<Student> {

    @Override
    public int compare(Student student1, Student student2) {
        return student1.getFullName().toLowerCase(Locale.ROOT).compareTo(student2.getFullName().toLowerCase(Locale.ROOT));
    }
}
