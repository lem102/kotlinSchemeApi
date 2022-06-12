package com.example.testApi

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class EvaluatorTests {

    @Test
    fun oneNumberAtomEvaluatesToTheSameAtom() {
        val input = listOf(NumberAtom(5.0f, Position(0,0), Position(0,0)))
        val output = performEvaluation(input)
        Assertions.assertTrue(output == input)
    }

    @Test
    fun oneStringAtomEvaluatesToItsValue() {
        val input = listOf(StringAtom("test", Position(0,0), Position(0,0)))
        val output = performEvaluation(input)
        Assertions.assertTrue(output == input)
    }

    @Test
    fun multipleAtomsEvaluateToTheirValues() {
        val input = listOf(NumberAtom(5.0f, Position(0,0), Position(0,0)),
                           StringAtom("test", Position(0,0), Position(0,0)))
        val output = performEvaluation(input)
        Assertions.assertTrue(output == input)
        Assertions.assertTrue(output[0] == input[0])
        Assertions.assertTrue(output[1] == input[1])
    }

    @Test
    fun identifierForBuiltInFunctionEvaluatesToThatFunction() {
        val input = listOf(IdentifierAtom("add", Position(0, 0), Position(0, 0)))
        val output = performEvaluation(input)
        val arg1 = NumberAtom(2.0f, Position(0,0), Position(0,0))
        val arg2 = NumberAtom(3.0f, Position(0,0), Position(0,0))
        val result = (output[0] as (Position, Position, NumberAtom, NumberAtom) -> NumberAtom)(Position(0,0), Position(0,0), arg1,arg2)
        println(result.value)
        Assertions.assertTrue(result.value == 5.0f)
    }
    
}
