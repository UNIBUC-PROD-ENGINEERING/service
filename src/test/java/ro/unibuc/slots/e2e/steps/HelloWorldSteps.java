package ro.unibuc.slots.e2e.steps;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import ro.unibuc.slots.dto.Greeting;
import ro.unibuc.slots.e2e.util.HeaderSetup;
import ro.unibuc.slots.e2e.util.ResponseErrorHandler;
import ro.unibuc.slots.e2e.util.ResponseResults;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@CucumberContextConfiguration
@SpringBootTest()
public class HelloWorldSteps {
    public static ResponseResults latestResponse = null;

    @Autowired
    protected RestTemplate restTemplate;

    @Given("^the client calls /hello-world")
    public void the_client_issues_GET_hello() {
        executeGet("http://localhost:8080/hello-world");
    }

    @Then("^the client receives status code of (\\d+)$")
    public void the_client_receives_status_code_of(int statusCode) throws Throwable {
        assertThat(
                "status code is incorrect : " + latestResponse.getBody(),
                latestResponse.getTheResponse().getStatusCode().value(),
                is(statusCode)
        );
    }

    @And("^the client receives response (.+)$")
    public void the_client_receives_response(String response) throws JsonProcessingException {
        final Greeting greeting = new ObjectMapper().readValue(latestResponse.getBody(), Greeting.class);
        assertThat("Response received is incorrect", greeting.getContent(), is(response));
    }

    public void executeGet(String url) {
        final Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        final HeaderSetup requestCallback = new HeaderSetup(headers);
        final ResponseErrorHandler errorHandler = new ResponseErrorHandler();

        restTemplate.setErrorHandler(errorHandler);
        latestResponse = restTemplate.execute(
                url,
                HttpMethod.GET,
                requestCallback,
                response -> errorHandler.getHadError() ? errorHandler.getResults() : new ResponseResults(response)
        );
    }
}
