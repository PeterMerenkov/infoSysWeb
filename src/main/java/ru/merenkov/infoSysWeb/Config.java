package ru.merenkov.infoSysWeb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.merenkov.infoSysWeb.group.Group;
import ru.merenkov.infoSysWeb.group.GroupRepos;
import ru.merenkov.infoSysWeb.student.Student;
import ru.merenkov.infoSysWeb.student.StudentRepos;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class Config {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepos studentRepos, GroupRepos groupRepos) {
        return args -> {

            Group iait = new Group(
                    4,
                    "IAIT"
            );

            Group mmt = new Group(
                    2,
                    "MMT"
            );

            groupRepos.saveAll(List.of(iait, mmt));

            Student peter = new Student(
                    "Merenkov Peter Romanovich",
                    LocalDate.of(2002, Month.APRIL, 15),
                    groupRepos.getById(1L)
            );

            Student oleg = new Student(
                    "Saenko Oleg Hezekovich",
                    LocalDate.of(2003, Month.DECEMBER, 9),
                    groupRepos.getById(1L)
            );

            studentRepos.saveAll(List.of(peter, oleg));
        };
    }
}
