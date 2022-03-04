package ru.merenkov.infoSysWeb.general;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.merenkov.infoSysWeb.group.Group;
import ru.merenkov.infoSysWeb.group.GroupService;
import ru.merenkov.infoSysWeb.student.Student;
import ru.merenkov.infoSysWeb.student.StudentService;

import java.io.*;
import java.time.LocalDate;

@Service
public class GeneralService {

    private final StudentService studentService;
    private final GroupService groupService;

    @Autowired
    public GeneralService(StudentService studentService, GroupService groupService) {
        this.studentService = studentService;
        this.groupService = groupService;
    }

    public void mergeDB(String path) {
        JSONParser parser = new JSONParser();

        //-----ser-------

        /*JSONObject rootJsonObject = null;

        try {
            FileInputStream fis = new FileInputStream(fileDB);
            ObjectInputStream ois = new ObjectInputStream(fis);
            rootJsonObject = (JSONObject) parser.parse(((JSONObject) ois.readObject()).toJSONString());
        } catch (IOException | ParseException | ClassNotFoundException e) {
            e.printStackTrace();
        }*/

        //---no-ser------

        JSONObject rootJsonObject = null;
        FileReader reader = null;
        try {
            reader = new FileReader(path);
            rootJsonObject = (JSONObject) parser.parse(reader);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        //--------

        JSONArray groupJSONArr = (JSONArray) rootJsonObject.get("groups");

        for(Object it : groupJSONArr) {
            JSONObject groupJSONObj = (JSONObject) it;


            Group tempGroup = new Group((Long) groupJSONObj.get("id"),
                    ((Long) groupJSONObj.get("groupNumber")).intValue(),
                    (String) groupJSONObj.get("faculty"));

            Long groupId = ((Long) groupJSONObj.get("id"));
            if (groupService.existsById(groupId)) {
                groupService.updateGroup(groupId, tempGroup);
            } else {
                groupService.addNewGroup(tempGroup);
            }
        }

        //--------

        JSONArray studentJSONArr = (JSONArray) rootJsonObject.get("students");

        for(Object it : studentJSONArr) {
            JSONObject studentJSONObj = (JSONObject) it;

            String[] tempDate = ((String) studentJSONObj.get("dateOfAdmission")).split("-");

            Student tempStudent = new Student(
                    (Long) studentJSONObj.get("id"),
                    (String) studentJSONObj.get("fullName"),
                    LocalDate.of(Integer.parseInt(tempDate[0]),Integer.parseInt(tempDate[1]), Integer.parseInt(tempDate[2])),
                    groupService.getGroupById((Long) studentJSONObj.get("groupId")));

            Long studentId = (Long) studentJSONObj.get("id");
            if (studentService.existsById(studentId)) {
                studentService.updateStudent(studentId, tempStudent);
            } else {
                studentService.addNewStudent(tempStudent);
            }
        }

        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //-------
    }

    public void exportDB(String path) {
        JSONObject root = new JSONObject();

        JSONArray studs = new JSONArray();
        JSONArray groups = new JSONArray();

        groups.addAll(groupService.getGroups());
        studs.addAll(studentService.getStudents());

        root.put("groups", groups);
        root.put("students", studs);

        try {
            try (FileWriter file = new FileWriter(path)) {
                file.write(root.toJSONString());
                file.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
