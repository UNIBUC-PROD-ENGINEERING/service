Feature: Book Management

  Scenario: Create a new book
    Given a book with title "The Great Gatsby" and author "F. Scott Fitzgerald"
    When the client sends a POST request to /books
    Then the client receives status code of 201

  Scenario: Get all books
    Given the client requests all books
    When the client sends a GET request to /books
    Then the client receives status code of 200
    And the client receives a list of books with size 1

  Scenario: Get a specific book
    Given a book with title "To Kill a Mockingbird" and author "Harper Lee"
    And the client requests a book with id "{bookId}"
    When the client sends a GET request to /books/{bookId}
    Then the client receives status code of 200
    And the client receives a book with title "To Kill a Mockingbird" and author "Harper Lee"

  Scenario: Delete a book
    Given a book with title "1984" and author "George Orwell"
    And the client requests all books
    When the client sends a DELETE request to /books/{bookId}
    Then the client receives status code of 204
    And the client requests all books
    And the client receives a list of books with size 0
