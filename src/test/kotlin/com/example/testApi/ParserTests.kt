package com.example.testApi

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.test.context.SpringBootTest
import com.example.testApi.lexicalAnalysis.performLexicalAnalysis
import com.example.testApi.lexicalAnalysis.TokenType
import com.example.testApi.lexicalAnalysis.Position
import com.example.testApi.lexicalAnalysis.Token
import com.example.testApi.parsing.performParsing
import com.example.testApi.parsing.NumberAtom
import com.example.testApi.parsing.SExpression
import com.example.testApi.parsing.StringAtom
import com.example.testApi.parsing.IdentifierAtom
import com.example.testApi.parsing.SExpressionList

@SpringBootTest
class ParserTests {

    @Test
    fun numberTokenGivesNumberAtom() {
        val input = listOf(Token(TokenType.NUMBER, "5", Position(1, 0), Position(1, 1)))
        val result = performParsing(input)
        Assertions.assertTrue(result[0] is NumberAtom)
	}

   	@Test
    fun stringTokenGivesStringAtom() {
        val input = listOf(Token(TokenType.STRING, "string", Position(1, 0), Position(1, 5)))
        val result = performParsing(input)
        Assertions.assertTrue(result[0] is StringAtom)
	}

    @Test
    fun identifierTokenGivesIdentifierAtom() {
        val input = listOf(Token(TokenType.IDENTIFIER, "identifier", Position(1, 0), Position(1, 5)))
        val result = performParsing(input)
        Assertions.assertTrue(result[0] is IdentifierAtom)
	}

    @Test
    fun positionDataFromTokenIsTransferredToSexpression() {
        val input = listOf(Token(TokenType.NUMBER, "2", Position(1, 0), Position(1, 1)))
        val result = performParsing(input)
        Assertions.assertTrue(input[0].start.row == result[0].start.row)
        Assertions.assertTrue(input[0].start.column == result[0].start.column)
        Assertions.assertTrue(input[0].end.row == result[0].end.row)
        Assertions.assertTrue(input[0].end.column == result[0].end.column)
    }

    @Test
    fun programsContainingUnbalancedExpressionsAreNotAllowed() {
        val input = listOf(Token(TokenType.OPENING_PARENTHESIS, "", Position(1, 0), Position(1, 1)),
                           Token(TokenType.OPENING_PARENTHESIS, "", Position(1, 0), Position(1, 1)),
                           Token(TokenType.CLOSING_PARENTHESIS, "", Position(1, 0), Position(1, 1)))
        Assertions.assertThrows(Exception::class.java, { performParsing(input) })
    }

    @Test
    fun programsContainingBalancedExpressionsAreAllowed() {
        val input = listOf(Token(TokenType.OPENING_PARENTHESIS, "", Position(1, 0), Position(1, 1)),
                           Token(TokenType.CLOSING_PARENTHESIS, "", Position(1, 0), Position(1, 1)))
        Assertions.assertDoesNotThrow({ performParsing(input) })
    }

    @Test
    fun balancedBracketsCreateAList() {
        val input = listOf(Token(TokenType.OPENING_PARENTHESIS, "", Position(1, 0), Position(1, 1)),
                           Token(TokenType.CLOSING_PARENTHESIS, "", Position(1, 0), Position(1, 1)))
        val result = performParsing(input)
        Assertions.assertTrue(result[0] is SExpressionList)
    }

    @Test
    fun tokensBetweenBalancedbracketsAreInsideTheList() {
        val input = listOf(Token(TokenType.OPENING_PARENTHESIS, "", Position(1, 0), Position(1, 1)),
                           Token(TokenType.NUMBER, "2", Position(1, 0), Position(1, 1)),
                           Token(TokenType.CLOSING_PARENTHESIS, "", Position(1, 0), Position(1, 1)))
        val result = performParsing(input)
        Assertions.assertTrue((result[0] as SExpressionList).expressions[0] is NumberAtom)
    }

    @Test
    fun `a list can contain any number of s-expressions including other lists`() {
        val input = listOf(Token(TokenType.OPENING_PARENTHESIS, "", Position(1, 0), Position(1, 1)),
                           Token(TokenType.NUMBER, "2", Position(1, 0), Position(1, 1)),
                           Token(TokenType.OPENING_PARENTHESIS, "", Position(1, 0), Position(1, 1)),
                           Token(TokenType.CLOSING_PARENTHESIS, "", Position(1, 0), Position(1, 1)),
                           Token(TokenType.CLOSING_PARENTHESIS, "", Position(1, 0), Position(1, 1)))
        val result = performParsing(input)
        Assertions.assertTrue((result[0] as SExpressionList).expressions[0] is NumberAtom)
        Assertions.assertTrue((result[0] as SExpressionList).expressions[1] is SExpressionList)
    }
}
