package ru.merenkov.infoSysWeb.group;

import javax.persistence.*;

@Entity
@Table(name = "stud_group")
public class Group implements Comparable<Group> {

    @Id
    @SequenceGenerator(
            name = "stud_group_sequence",
            sequenceName = "stud_group_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "stud_group_sequence"
    )
    private Long id;
    private Integer groupNumber;
    private String faculty;

    public Group() {
    }

    public Group(Integer groupNumber, String faculty) {
        this.groupNumber = groupNumber;
        this.faculty = faculty;
    }

    public Group(Long id, Integer groupNumber, String faculty) {
        this.id = id;
        this.groupNumber = groupNumber;
        this.faculty = faculty;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(Integer groupNumber) {
        this.groupNumber = groupNumber;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", groupNumber=" + groupNumber +
                ", faculty='" + faculty + '\'' +
                '}';
    }

    @Override
    public int compareTo(Group otherGroup){
        if (this.toString().compareTo(otherGroup.toString()) > 0) {
            return 1;
        }
        else if (this.toString().compareTo(otherGroup.toString()) < 0) {
            return -1;
        }
        else {
            return 0;
        }
    }
}
