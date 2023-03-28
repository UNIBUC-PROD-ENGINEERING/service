Feature: get review by id throws error if the review doesn't exists
  Scenario: client makes call to GET /reviews/get-review/{reviewId}
    When the client calls /review/get-review/{reviewId} with ID non_existing_review_id
    Then the client receives response with status code 400
    And the client receives the message Entity: review was not found