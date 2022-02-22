package ru.merenkov.infoSysWeb.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.merenkov.infoSysWeb.group.Group;
import ru.merenkov.infoSysWeb.group.GroupRepos;
import ru.merenkov.infoSysWeb.student.comparators.StudentDateComparator;
import ru.merenkov.infoSysWeb.student.comparators.StudentFullNameComparator;
import ru.merenkov.infoSysWeb.student.comparators.StudentGroupComparator;
import ru.merenkov.infoSysWeb.student.comparators.StudentIdComparator;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
public class StudentService {

    private final StudentRepos studentRepos;
    private final GroupRepos groupRepos;

    @Autowired
    public StudentService(StudentRepos studentRepos, GroupRepos groupRepos) {
        this.studentRepos = studentRepos;
        this.groupRepos = groupRepos;
    }


    public List<Student> getStudents() {
        return studentRepos.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepos.getById(id);
    }

    @Transactional
    public void updateStudent(Long studentId, Student otherStudent) {

        Student student = studentRepos.findById(studentId).orElseThrow(
                () -> new IllegalStateException(
                "student w id " + studentId + " doesn't exist!"
                ));

        if (otherStudent.getFullName() != null &&
                otherStudent.getFullName() .length() > 0 &&
                !Objects.equals(student.getFullName(), otherStudent.getFullName() )) {

            student.setFullName(otherStudent.getFullName() );
        }

        if (otherStudent.getDateOfAdmission() != null && !Objects.equals(student.getDateOfAdmission(), otherStudent.getDateOfAdmission())) {

            student.setDateOfAdmission(otherStudent.getDateOfAdmission() );
        }

        Group group = groupRepos.findById(otherStudent.getStudentGroup().getId()).orElseThrow(() -> new IllegalStateException(
                "group w id " + otherStudent.getStudentGroup().getId() + " doesn't exist!"
        ));

        student.setStudentGroup(group);
    }

    public void addNewStudent(Student student) {

        studentRepos.save(student);
    }

    public void deleteStudent(Long studentId) {

        boolean exists = studentRepos.existsById(studentId);

        if (!exists) {
            throw new IllegalStateException(
                    "student w id: " + studentId + " doesn't exist!"
            );
        }
        studentRepos.deleteById(studentId);
    }

    public List<Student> searchStudents(String needle) {
        List<Student> searchedStudent = new ArrayList<>();

        for (Student student : getStudents()) {
            if (student.getFullName().toLowerCase(Locale.ROOT).contains(needle.toLowerCase(Locale.ROOT))) {
                searchedStudent.add(student);
            }
        }

        return searchedStudent;
    }

    public List<Student> getStudentsSortedBy(String sort) {

        List<Student> sortedStudents = getStudents();

        switch (sort) {
            case "ById" -> sortedStudents.sort(new StudentIdComparator());
            case "ByFullName" -> sortedStudents.sort(new StudentFullNameComparator());
            case "ByDateOfAdmission" -> sortedStudents.sort(new StudentDateComparator());
            case "ByGroup" -> sortedStudents.sort(new StudentGroupComparator());
        }

        return sortedStudents;
    }
}
