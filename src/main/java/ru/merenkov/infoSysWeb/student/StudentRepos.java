package ru.merenkov.infoSysWeb.student;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepos extends JpaRepository<Student, Long> {
}
