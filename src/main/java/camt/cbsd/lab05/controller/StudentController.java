package camt.cbsd.lab05.controller;

import camt.cbsd.lab05.entity.Student;
import camt.cbsd.lab05.service.StudentService;
import camt.cbsd.lab05.service.StudentServiceImpl;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.websocket.server.PathParam;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.List;

@Profile("firstDataSource")
@ConfigurationProperties(prefix = "server")
@RestController
public class StudentController {
    StudentService studentService;

    public String getImageServerDir() {
        return imageServerDir;
    }

    public void setImageServerDir(String imageServerDir) {
        System.out.println();
        this.imageServerDir = imageServerDir;
    }

    String imageServerDir;
    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/student")
    public ResponseEntity<?> getStudents() {

        List<Student> students = studentService.getStudents();
        return ResponseEntity.ok(students);
    }

    @GetMapping("student/{id}")
    public ResponseEntity getStudent(@PathVariable("id")long id){
        Student student = studentService.findById(id);
        if (student!= null)
            return ResponseEntity.ok(student);
        else
            //http code 204
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }
    @PostMapping("/student")
    public ResponseEntity<?> uploadOnlyStudent(@RequestBody Student student){
        studentService.addStudent(student);
        return ResponseEntity.ok(student);
    }
    @GetMapping(
            value = "/images/{fileName:.+}",
            produces = {MediaType.IMAGE_GIF_VALUE,MediaType.IMAGE_JPEG_VALUE,MediaType.IMAGE_PNG_VALUE}
    )
    public @ResponseBody
    ResponseEntity<?> getStudentImage(@PathVariable("fileName")String fileName){
        try {
            File file = Paths.get(imageServerDir + fileName).toFile();
            InputStream in = new FileInputStream(file);

            return ResponseEntity.ok(IOUtils.toByteArray(in));
        }catch (IOException e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }



    }
    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file")MultipartFile file){
        if(file.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        }try{
            byte[] bytes = file.getBytes();
            String oldFilename = file.getOriginalFilename();
            String ext = FilenameUtils.getExtension(oldFilename);
            String newFilename = Integer.toString(LocalTime.now().hashCode(),16)+Integer.toString(oldFilename.hashCode(),16)+"."+ext;
            Path path = Paths.get(imageServerDir+newFilename);
            Files.write(path,bytes);
            return ResponseEntity.ok(newFilename);

        }catch (IOException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    };
}
