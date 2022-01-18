Feature: GetHeroes

  Scenario: return all heroes
    When Users requests api list data
    Then Should return the a list with 122 heroes

  Scenario: return all heroes alphabetic
    When Users requests api list data
    Then The heroes id order should be ascending
