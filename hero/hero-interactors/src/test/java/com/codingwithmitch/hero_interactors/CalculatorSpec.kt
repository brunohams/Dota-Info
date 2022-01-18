package com.codingwithmitch.hero_interactors

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import org.spekframework.spek2.style.specification.describe

object CalculatorSpec: Spek({
    Feature("Calculator") {
        val calculator by memoized{ Calculator() }

        Scenario("Sum two numbers") {
            var a = 0
            var b = 0
            var result = 0
            val expectedResult = 4

            Given("initial a and b") {
                a = 2
                b = 2
            }

            When("sum a with b") {
                result = calculator.add(a, b)
            }

            Then("it should return a sum with a and b") {
                assert(result == expectedResult)
            }

        }
    }
})