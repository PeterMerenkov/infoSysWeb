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

        JSONObject rootJsonObject = null;
        FileReader reader = null;
        try {

            reader = new FileReader(path);
            rootJsonObject = (JSONObject) parser.parse(reader);


            //--------

            JSONArray groupJSONArr = (JSONArray) rootJsonObject.get("groups");

            for(Object it : groupJSONArr) {
                JSONObject groupJSONObj = (JSONObject) it;


                Group tempGroup = new Group(
                        ((Long) groupJSONObj.get("groupNumber")).intValue(),
                        (String) groupJSONObj.get("faculty"));

                boolean isThereGroupId = groupJSONObj.containsKey("id");
                if (isThereGroupId && groupService.existsById((Long) groupJSONObj.get("id"))) {
                    groupService.updateGroup((Long) groupJSONObj.get("id"), tempGroup);
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
                        (String) studentJSONObj.get("fullName"),
                        LocalDate.of(Integer.parseInt(tempDate[0]),Integer.parseInt(tempDate[1]), Integer.parseInt(tempDate[2])),
                        groupService.getGroupById((Long) studentJSONObj.get("groupId")));

                boolean isThereStudId = studentJSONObj.containsKey("id");
                if (isThereStudId && studentService.existsById((Long) studentJSONObj.get("id"))) {
                    studentService.updateStudent((Long) studentJSONObj.get("id"), tempStudent);
                } else {
                    studentService.addNewStudent(tempStudent);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        } finally {

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

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
