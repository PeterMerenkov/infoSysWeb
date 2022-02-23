package ru.merenkov.infoSysWeb.student;

import org.springframework.format.annotation.DateTimeFormat;
import ru.merenkov.infoSysWeb.group.Group;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "student")
public class Student {

    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    private Long id;

    private String fullName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfAdmission;

    @ManyToOne
    @JoinColumn(name = "stud_group_id",
            foreignKey = @ForeignKey(name = "GROUP_ID_FK")
    )
    private Group studentGroup;

    public Student() {
    }

    public Student(String fullName, LocalDate dateOfAdmission, Group studentGroup) {
        this.fullName = fullName;
        this.dateOfAdmission = dateOfAdmission;
        this.studentGroup = studentGroup;
    }

    public Student(Long id, String fullName, LocalDate dateOfAdmission, Group studentGroup) {
        this.id = id;
        this.fullName = fullName;
        this.dateOfAdmission = dateOfAdmission;
        this.studentGroup = studentGroup;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getDateOfAdmission() {
        return dateOfAdmission;
    }

    public void setDateOfAdmission(LocalDate dateOfAdmission) {
        this.dateOfAdmission = dateOfAdmission;
    }

    public Group getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(Group studentGroup) {
        this.studentGroup = studentGroup;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", dateOfAdmission=" + dateOfAdmission +
                ", group=" + studentGroup +
                '}';
    }
}
