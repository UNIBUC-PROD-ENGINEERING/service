package ro.unibuc.hello.e2e.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import ro.unibuc.hello.data.BookEntity;
import ro.unibuc.hello.data.BookRepository;
import ro.unibuc.hello.e2e.util.HeaderSetup;
import ro.unibuc.hello.e2e.util.ResponseErrorHandler;
import ro.unibuc.hello.e2e.util.ResponseResults;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@CucumberContextConfiguration
@SpringBootTest()

public class BookSteps {
    public static ResponseResults latestResponse = null;

    @Autowired
    protected RestTemplate restTemplate;

    @Autowired
    private BookRepository bookRepository;

    private String bookId;

    @Given("^the client creates a book with title \"(.*)\" and author \"(.*)\"$")
    public void the_client_creates_a_book(String title, String author) {
        BookEntity book = new BookEntity(title, author);
        BookEntity savedBook = bookRepository.save(book);
        bookId = savedBook.getId();
    }

    @Then("^the client receives status code of (\\d+)$")
    public void the_client_receives_status_code_of(int statusCode) throws Throwable {
        final HttpStatus currentStatusCode = latestResponse.getTheResponse().getStatusCode();
        assertThat("status code is incorrect : " + latestResponse.getBody(), currentStatusCode.value(), is(statusCode));
    }

    @And("^the client receives a list of books with size (\\d+)$")
    public void the_client_receives_list_of_books_with_size(int size) throws JsonProcessingException {
        String responseBody = latestResponse.getBody();
        @SuppressWarnings("unchecked")
        List<BookEntity> books = new ObjectMapper().readValue(responseBody, List.class);
        assertThat("Number of books received is incorrect", books.size(), is(size));
    }

    @And("^the client receives a book with title \"(.*)\" and author \"(.*)\"$")
    public void the_client_receives_book_with_title_and_author(String title, String author) throws JsonProcessingException {
        String responseBody = latestResponse.getBody();
        BookEntity book = new ObjectMapper().readValue(responseBody, BookEntity.class);
        assertThat("Title of the book received is incorrect", book.getTitle(), is(title));
        assertThat("Author of the book received is incorrect", book.getAuthor(), is(author));
    }

    @Given("^the client requests all books$")
    public void the_client_requests_all_books() {
        executeGet("http://localhost:8080/books");
    }

    @Given("^the client requests a book with id \"(.*)\"$")
    public void the_client_requests_book_with_id(String id) {
        executeGet("http://localhost:8080/books/" + id);
    }

    @And("^the client deletes the book with id \"(.*)\"$")
    public void the_client_deletes_book_with_id(String id) {
        restTemplate.delete("http://localhost:8080/books/" + id);
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
