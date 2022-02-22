package ru.merenkov.infoSysWeb.student.comparators;

import ru.merenkov.infoSysWeb.student.Student;

import java.util.Comparator;
import java.util.Locale;

public class StudentGroupComparator implements Comparator<Student> {

    @Override
    public int compare(Student student1, Student student2) {
        return student1.getStudentGroup().toString().toLowerCase(Locale.ROOT).compareTo(
                student2.getStudentGroup().toString().toLowerCase(Locale.ROOT));
    }
}
