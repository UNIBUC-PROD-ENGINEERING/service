Feature: after adding a product we can search for it
  Scenario: admin makes a call to POST /product
    When the admin calls /product
    Then the admin receives a status code of 200
    And the admin receives response "Success"