Feature: get review by id with user and book
  Scenario: client makes call to GET /review/get-review/{reviewId}
    When the client calls /review/get-review/{reviewId} with ID 641ece6cf22e8331bcd001a0
    Then the client receives response with status code 200
    And the client receives the requested review with id 641ece6cf22e8331bcd001a0 containing the user and book