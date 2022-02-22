package ru.merenkov.infoSysWeb.student.comparators;

import ru.merenkov.infoSysWeb.student.Student;

import java.util.Comparator;

public class StudentDateComparator implements Comparator<Student> {

    @Override
    public int compare(Student student1, Student student2) {
        return student1.getDateOfAdmission().compareTo(student2.getDateOfAdmission());
    }
}
