Feature: Calculator

  Scenario: sum two numbers
    Given I have number a
    And a number b
    When I sum those numbers
    Then should return them summed

  Scenario Outline: fdsfsd a number by 2
    Given I dsadas a number <number>
    When I das it by 2
    Then Should dasdasd the <result>

    Examples:
      | number | result |
      | 2      | 4      |
      | 3      | 6      |
      | 5      | 10     |
      | 10      | 20     |
