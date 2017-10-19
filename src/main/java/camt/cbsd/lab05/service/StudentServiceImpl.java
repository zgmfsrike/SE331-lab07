package camt.cbsd.lab05.service;

import camt.cbsd.lab05.dao.StudentDao;
import camt.cbsd.lab05.entity.Student;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Profile("firstDataSource")
@ConfigurationProperties(prefix = "server")
@Service
@JsonIgnoreProperties(ignoreUnknown = true)
/*
{
    Answer 1.3
    "timestamp": 1508424929599,
    "status": 400,
    "error": "Bad Request",
    "exception": "org.springframework.http.converter.HttpMessageNotReadableException",
    "message": "JSON parse error: Can not construct instance of camt.cbsd.lab05.entity.Student: no suitable constructor found, can not deserialize from Object value (missing default constructor or creator, or perhaps need to add/enable type information?); nested exception is com.fasterxml.jackson.databind.JsonMappingException: Can not construct instance of camt.cbsd.lab05.entity.Student: no suitable constructor found, can not deserialize from Object value (missing default constructor or creator, or perhaps need to add/enable type information?)\n at [Source: java.io.PushbackInputStream@760c8cff; line: 2, column: 2]",
    "path": "/student"
}
 */
public class StudentServiceImpl implements StudentService {
    String imageBaseUrl;
    String baseUrl;
    String imageUrl;
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @PostConstruct
    protected void setImageBaseUrl(){
        this.imageBaseUrl = this.baseUrl + this.imageUrl;
    }

    @Autowired
    StudentDao studentDao;
    public List<Student> getStudents(){

        return studentDao.getStudents();
    }

    @Override
    public Student findById(long id) {
        return studentDao.findById(id);
    }


}
