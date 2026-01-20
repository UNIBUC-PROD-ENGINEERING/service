package ro.unibuc.hello.e2e.steps;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.dto.AssignTodoRequest;
import ro.unibuc.hello.dto.CreateTodoRequest;
import ro.unibuc.hello.dto.CreateUserRequest;
import ro.unibuc.hello.dto.TodoResponse;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TodoSteps {

    private static final String BASE_URL = "http://localhost:8080";

    private final RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    private final ObjectMapper objectMapper = new ObjectMapper();

    private ResponseEntity<String> latestResponse;
    private final List<String> createdUserIds = new ArrayList<>();
    private final List<String> createdTodoIds = new ArrayList<>();
    private String lastCreatedTodoId;

    @After
    public void cleanup() {
        // Delete todos first (they reference users)
        for (String todoId : createdTodoIds) {
            try {
                restTemplate.delete(BASE_URL + "/todos/" + todoId);
            } catch (Exception e) {
                // Ignore errors during cleanup
            }
        }
        createdTodoIds.clear();

        // Then delete users
        for (String userId : createdUserIds) {
            try {
                restTemplate.delete(BASE_URL + "/users/" + userId);
            } catch (Exception e) {
                // Ignore errors during cleanup
            }
        }
        createdUserIds.clear();
    }

    @Given("a user named {word} with email {word}")
    public void createUser(String name, String email) throws Exception {
        CreateUserRequest request = new CreateUserRequest(name, email);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateUserRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(BASE_URL + "/users", entity, String.class);
        UserEntity user = objectMapper.readValue(response.getBody(), UserEntity.class);
        createdUserIds.add(user.id());
    }

    @When("the client creates a todo {string} for {word}")
    public void createTodo(String description, String email) throws Exception {
        CreateTodoRequest request = new CreateTodoRequest(description, email);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateTodoRequest> entity = new HttpEntity<>(request, headers);

        latestResponse = restTemplate.postForEntity(BASE_URL + "/todos", entity, String.class);
        TodoResponse todo = objectMapper.readValue(latestResponse.getBody(), TodoResponse.class);
        createdTodoIds.add(todo.id());
        lastCreatedTodoId = todo.id();
    }

    @Then("the client receives status code of {int}")
    public void verifyStatusCode(int statusCode) {
        assertThat("status code is incorrect", latestResponse.getStatusCode().value(), is(statusCode));
    }

    @Then("the client can retrieve {int} todo(s) for {word}")
    public void verifyTodoCount(int count, String email) throws Exception {
        String url = BASE_URL + "/todos?assigneeEmail=" + email;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        List<TodoResponse> todos = objectMapper.readValue(response.getBody(), new TypeReference<List<TodoResponse>>() {});
        assertThat("todo count is incorrect", todos.size(), is(count));
    }

    @When("the client reassigns the todo to {word}")
    public void reassignTodo(String newAssigneeEmail) {
        AssignTodoRequest request = new AssignTodoRequest(newAssigneeEmail);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<AssignTodoRequest> entity = new HttpEntity<>(request, headers);

        restTemplate.exchange(BASE_URL + "/todos/" + lastCreatedTodoId + "/assignee",
                HttpMethod.PATCH, entity, String.class);
    }

    @When("the client marks the todo as done")
    public void markTodoAsDone() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Boolean> entity = new HttpEntity<>(true, headers);

        restTemplate.patchForObject(BASE_URL + "/todos/" + lastCreatedTodoId + "/done", entity, String.class);
    }

    @Then("the todo {string} for {word} is marked as done")
    public void verifyTodoIsDone(String description, String email) throws Exception {
        String url = BASE_URL + "/todos?assigneeEmail=" + email;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        List<TodoResponse> todos = objectMapper.readValue(response.getBody(), new TypeReference<List<TodoResponse>>() {});
        TodoResponse todo = todos.stream()
                .filter(t -> t.description().equals(description))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Todo not found: " + description));

        assertThat("todo should be marked as done", todo.done(), is(true));
    }
}
