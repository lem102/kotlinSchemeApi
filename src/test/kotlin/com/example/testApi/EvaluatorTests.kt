package com.example.testApi

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions
import org.springframework.boot.test.context.SpringBootTest
import com.example.testApi.parsing.SExpression
import com.example.testApi.parsing.NumberAtom
import com.example.testApi.parsing.StringAtom
import com.example.testApi.parsing.IdentifierAtom
import com.example.testApi.lexicalAnalysis.Position
import com.example.testApi.evaluation.performEvaluation

@SpringBootTest
class EvaluatorTests {

    @Test
    fun oneNumberAtomEvaluatesToItsValue() {
        val input = listOf(NumberAtom(5.0f, Position(0,0), Position(0,0)))
        val output = performEvaluation(input)
        Assertions.assertTrue(output[0] == 5.0f)
    }

    @Test
    fun oneStringAtomEvaluatesToItsValue() {
        val input = listOf(StringAtom("test", Position(0,0), Position(0,0)))
        val output = performEvaluation(input)
        Assertions.assertTrue(output[0] == "test")
    }

    @Test
    fun oneIdentifierAtomEvaluatesToItsValue() {
        val input = listOf(IdentifierAtom("test", Position(0, 0), Position(0, 0)))
        val output = performEvaluation(input)
        Assertions.assertTrue(output[0] == "test")
    }
}
