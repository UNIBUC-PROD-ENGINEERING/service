package com.bookstore.v1.e2e.steps;

import com.bookstore.v1.dto.ReviewDTO;
import com.bookstore.v1.e2e.utils.HeaderSetup;
import com.bookstore.v1.e2e.utils.ResponseErrorHandler;
import com.bookstore.v1.e2e.utils.ResponseResults;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

@CucumberContextConfiguration
@SpringBootTest()
public class GetReviewByIdSteps {
    public static ResponseResults latestResponse = null;

    @Autowired
    protected RestTemplate restTemplate;

    @Autowired
    protected ObjectMapper objectMapper;

    @Given("^the client calls /review/get-review/\\{reviewId\\} with ID (\\w+)$")
    public void the_client_calls_review_get_review_reviewId_with_ID(String reviewId) throws Throwable {
        executeGet("http://localhost:8080/review/get-review/" + reviewId);
    }

    @Then("^the client receives response with status code (\\d+)$")
    public void the_client_receives_response_with_status_code(int statusCode) throws Throwable {
        final HttpStatus currentStatusCode = latestResponse.getTheResponse().getStatusCode();
        assertThat("Status code is incorrect: " + latestResponse.getBody(), currentStatusCode.value(), is(statusCode));
    }

    @And("^the client receives the requested review with id (\\w+) containing the user and book$")
    public void the_client_receives_the_requested_review_with_id_containing_the_user_and_book(String reviewId) throws
            Throwable {
        final String latestResponseBody = latestResponse.getBody();
        ReviewDTO reviewDTO = objectMapper.readValue(latestResponseBody, ReviewDTO.class);
        assertThat("ReviewDTO is null", reviewDTO != null);
        assertThat("ReviewDTO user is null", reviewDTO.getUser() != null);
        assertThat("ReviewDTO book is null", reviewDTO.getBook() != null);
        assertThat("Returned other review", reviewDTO.getId(), is(reviewId));
    }

    @And("^the client receives the message (.+)$")
    public void the_client_receives_the_message(String message) throws Throwable {
        final String latestResponseBody = latestResponse.getBody();
        assertThat("Message is incorrect: " + latestResponseBody + " ; or is missing", latestResponseBody,
                containsString(message));
    }


    public void executeGet(String url) {
        final Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        final HeaderSetup requestCallback = new HeaderSetup(headers);
        final ResponseErrorHandler errorHandler = new ResponseErrorHandler();

        restTemplate.setErrorHandler(errorHandler);
        latestResponse = restTemplate.execute(url, HttpMethod.GET, requestCallback, response -> {
            if (errorHandler.getHadError()) {
                return (errorHandler.getResults());
            } else {
                return (new ResponseResults(response));
            }
        });
    }
}
