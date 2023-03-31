package ro.unibuc.hello.controller;


import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.classic.methods.HttpGet;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.classic.methods.HttpPatch;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.classic.methods.HttpUriRequest;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.ContentType;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.io.entity.StringEntity;
import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import ro.unibuc.hello.dto.TeacherDto;
import ro.unibuc.hello.models.TeacherEntity;
import ro.unibuc.hello.repositories.TeacherRepository;

import java.io.IOException;
import java.net.http.HttpResponse;


import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
@Tag("IT")
public class TeacherControllerTestIT {

    @Autowired
    public TeacherRepository teacherRepository;

    @Autowired
    public ModelMapper modelMapper;


    @Test
    public void test_update_teacher_controller() throws IOException, JSONException {


        TeacherDto newTeacher = new TeacherDto();
        newTeacher.setFirstName("Aurel");
        newTeacher.setLastName("Vlaicu");
        newTeacher.setSubject("matematica");

        TeacherEntity teacherEntity = modelMapper.map(newTeacher, TeacherEntity.class);
        teacherEntity = teacherRepository.save(teacherEntity);

        String id = teacherEntity.getId();

        JSONObject json = new JSONObject();
        json.put("firstName", "AurelUpdatat");

        HttpUriRequest request = new HttpPatch( createURLWithPort("/"+id+ "/teacher"));
        StringEntity entity = new StringEntity(json.toString(), ContentType.APPLICATION_JSON);
        request.setEntity(entity);


        CloseableHttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );

        System.out.print(httpResponse.getCode());

        int statusCode = httpResponse.getCode();
        Assertions.assertEquals(HttpStatus.OK.value(), statusCode);

        teacherEntity = teacherRepository.findByFirstNameAndLastName("AurelUpdatat", "Vlaicu");

        if (teacherEntity != null) {
            teacherRepository.delete(teacherEntity);
        }

        Assertions.assertNotNull(teacherEntity);

    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + 8080 + uri;
    }
}
