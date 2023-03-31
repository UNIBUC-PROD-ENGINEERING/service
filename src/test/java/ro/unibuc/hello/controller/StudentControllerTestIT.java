package ro.unibuc.hello.controller;

import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.classic.methods.HttpPost;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.classic.methods.HttpUriRequest;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.ContentType;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.io.entity.EntityUtils;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.io.entity.StringEntity;
import io.cucumber.messages.internal.com.google.gson.Gson;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;

import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonNode;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import ro.unibuc.hello.dto.StudentDto;
import ro.unibuc.hello.models.StudentEntity;
import ro.unibuc.hello.repositories.StudentRepository;
import ro.unibuc.hello.repositories.TeacherRepository;

import java.time.LocalDate;

@SpringBootTest
@Tag("IT")
public class StudentControllerTestIT {

    @Autowired
    public StudentRepository studentRepository;

    @Autowired
    public ModelMapper modelMapper;

    @Test
    public void testAddStudent() throws Exception {
        StudentDto studentDto = new StudentDto();
        studentDto.setFirstName("John");
        studentDto.setLastName("Popescu");
        studentDto.setClassName("10A");
        //studentDto.setBirthDay(LocalDate.parse("2000-11-03"));

        JSONObject json = new JSONObject(new Gson().toJson(studentDto));
        json.put("birthDay", "2000-11-03");

        HttpUriRequest request = new HttpPost(createURLWithPort("/student"));
        request.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        request.setEntity(new StringEntity(json.toString(), ContentType.APPLICATION_JSON));

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build();
             CloseableHttpResponse response = httpClient.execute(request)) {

            String responseBody = EntityUtils.toString(response.getEntity());
            int statusCode = response.getCode();

            Assertions.assertEquals(HttpStatus.OK.value(), statusCode);

            JsonNode jsonNode = new ObjectMapper().readTree(responseBody);
            Assertions.assertTrue(jsonNode.get("success").asBoolean());
            StudentEntity student = studentRepository.findByFirstNameAndLastName("John", "Popescu");
            if (student != null) {
                studentRepository.delete(student);
            }
            Assertions.assertNotNull(student);

        }
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + 8080 + uri;
    }

}