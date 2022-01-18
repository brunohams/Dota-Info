Feature: Calculator

  Scenario: sum two numbers
    Given I have number a
    And a number b
    When I sum those numbers
    Then should return them summed

  Scenario Outline: multiply a number by 2
    Given I have a number <number>
    When I multiply it by 2
    Then Should return the <result>

    Examples:
      | number | result |
      | 2      | 4      |
      | 3      | 6      |
      | 5      | 10     |
      | 10      | 20     |