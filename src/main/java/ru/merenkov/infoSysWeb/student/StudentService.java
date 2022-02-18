package ru.merenkov.infoSysWeb.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.merenkov.infoSysWeb.group.Group;
import ru.merenkov.infoSysWeb.group.GroupRepos;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
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

    @Transactional
    public void updateStudent(Long studentId, String fullName, LocalDate dateOfAdmission, Long groupId) {

        Student student = studentRepos.findById(studentId).orElseThrow(
                () -> new IllegalStateException(
                "student w id " + studentId + " doesn't exist!"
                ));

        if (fullName != null &&
                fullName.length() > 0 &&
                !Objects.equals(student.getFullName(), fullName)) {

            student.setFullName(fullName);
        }

        if (dateOfAdmission != null && !Objects.equals(student.getDateOfAdmission(), dateOfAdmission)) {

            student.setDateOfAdmission(dateOfAdmission);
        }

        Group group = groupRepos.findById(groupId).orElseThrow(() -> new IllegalStateException(
                "group w id " + groupId + " doesn't exist!"
        ));

        student.setGroup(group);
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
}
