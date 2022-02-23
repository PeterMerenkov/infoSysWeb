package ru.merenkov.infoSysWeb.student.comparators;

import ru.merenkov.infoSysWeb.student.Student;

import java.util.Comparator;
import java.util.Locale;

public class StudentGroupComparator implements Comparator<Student> {

    @Override
    public int compare(Student student1, Student student2) {
        String str1 = student1.getStudentGroup().getFaculty() + "-" + student1.getStudentGroup().getGroupNumber();
        String str2 = student2.getStudentGroup().getFaculty() + "-" + student2.getStudentGroup().getGroupNumber();

        return str1.toLowerCase(Locale.ROOT).compareTo(str2.toLowerCase(Locale.ROOT));
    }
}
