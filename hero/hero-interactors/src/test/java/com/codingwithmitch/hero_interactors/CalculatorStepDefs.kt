package com.codingwithmitch.hero_interactors

import io.cucumber.java8.En
import io.cucumber.java8.PendingException
import io.cucumber.java8.Scenario
import io.cucumber.java8.StepDefinitionBody
import io.cucumber.java8.StepDefinitionBody.A0
import junit.framework.Assert.assertEquals

class CalculatorStepDefs: En {
    init {
        `sum two numbers`()
        `multiply a number by 2`()
    }

    private fun `sum two numbers`() {
        var a = 0
        var b = 0
        var result = 0
        Given("I have number a") {
            a = 2
        }

        Given("a number b") {
            b = 3
        }

        When("I sum those numbers") {
            result = a + b
        }

        Then("should return them summed") {
            assert(result == 5)
        }
    }

    private fun `multiply a number by 2`() {
        var number = 0
        var result = 0
        Given("I have a number {int}") { int1: Int? ->
            number = int1!!
        }
        When("I multiply it by {int}") { int1: Int? ->
            result = number * int1!!
        }
        Then("Should return the {int}") { int1: Int? ->
            assertEquals(int1, result)
        }
    }

}