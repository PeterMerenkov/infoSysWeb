package ru.merenkov.infoSysWeb.general;


import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
@RequestMapping
public class GeneralController {

    private final GeneralService generalService;

    @Autowired
    public GeneralController(GeneralService generalService) {
        this.generalService = generalService;
    }

    @GetMapping(path = "/import/error")
    public String getError() {
        return "studentDeleteError";
    }

    @GetMapping
    public String getIndexPage() {
        return "index";
    }

    @GetMapping(path = "/import")
    public String importDB() {
        return "import";
    }

    @PostMapping(path = "/import")
    public String mergeDB(@RequestParam("fileDB") MultipartFile fileDB) throws IOException {

        String fileName = "importDB.json";

        String uploadDir = "./jsonDB/";

        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        InputStream inputStream = fileDB.getInputStream();

        Path filePath = uploadPath.resolve(fileName);
        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        /*try {
        } catch (IOException e) {
            throw new IOException("Cannot upload file!");
        }*/

        generalService.mergeDB("D:\\myFiles\\study\\netCracker\\java\\infoSysWeb\\jsonDB\\" + fileName);

        return "index";
    }

    @GetMapping(path = "downloadDB.json")
    public String getExportPage() {
        return "export";
    }

    @PostMapping(
            value = "downloadDB.json",
            produces = MediaType.APPLICATION_NDJSON_VALUE
    )
    public @ResponseBody byte[] exportDB() throws IOException {
        String path = "D:\\myFiles\\study\\netCracker\\java\\infoSysWeb\\jsonDB\\exportDB.json";

        generalService.exportDB(path);

        InputStream in = new FileInputStream(path);


        return IOUtils.toByteArray(in);
    }
}
