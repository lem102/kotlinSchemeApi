package com.example.testApi

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions
import org.springframework.boot.test.context.SpringBootTest
import com.example.testApi.lexicalAnalysis.performLexicalAnalysis
import com.example.testApi.lexicalAnalysis.TokenType
import com.example.testApi.lexicalAnalysis.Position
import com.example.testApi.lexicalAnalysis.Token

@SpringBootTest
class LexicalAnalysisTests {

	@Test
	fun singleIdentifierIsReadCorrectly() {
        val result = performLexicalAnalysis("test")
        Assertions.assertEquals(result, listOf(Token(TokenType.IDENTIFIER,
                                                     "test",
                                                     Position(1,0),
                                                     Position(1,4))))
	}

	@Test
	fun singleNumberIsReadCorrectly() {
        val result = performLexicalAnalysis("2")
        Assertions.assertEquals(result, listOf(Token(TokenType.NUMBER,
                                                     "2",
                                                     Position(1,0),
                                                     Position(1,1))))
	}

    @Test
	fun singleStringIsReadCorrectly() {
        val result = performLexicalAnalysis("\"test\"")
        Assertions.assertEquals(result, listOf(Token(TokenType.STRING,
                                                     "\"test\"",
                                                     Position(1,0),
                                                     Position(1,6))))
	}

    @Test
	fun openingParenthesesIsReadCorrectly() {
        val result = performLexicalAnalysis("(")
        Assertions.assertEquals(result, listOf(Token(TokenType.OPENING_PARENTHESIS, "", Position(1,0), Position(1,1))))
	}

    @Test
	fun closingParenthesesIsReadCorrectly() {
        val result = performLexicalAnalysis(")")
        Assertions.assertEquals(result, listOf(Token(TokenType.CLOSING_PARENTHESIS,
                                                     "",
                                                     Position(1,0),
                                                     Position(1,1))))
	}

    @Test
	fun severalOfElementsAreReadCorrectly() {
        val result = performLexicalAnalysis("1 22 3")
        Assertions.assertEquals(result, listOf(Token(TokenType.NUMBER, "1", Position(1,0), Position(1,1)),
                                               Token(TokenType.NUMBER, "22", Position(1,2), Position(1,4)),
                                               Token(TokenType.NUMBER, "3", Position(1,5), Position(1,6))))
	}

    @Test
    fun identifierWrappedInParens() {
        val result = performLexicalAnalysis("(test)")
        Assertions.assertEquals(result, listOf(Token(TokenType.OPENING_PARENTHESIS, "", Position(1,0), Position(1,1)),
                                               Token(TokenType.IDENTIFIER, "test", Position(1,1), Position(1,5)),
                                               Token(TokenType.CLOSING_PARENTHESIS, "", Position(1,5), Position(1,6))))
    }

    @Test
    fun commentWithWhitespaceAfterWillNotBeIncludedInOutput() {
        val result = performLexicalAnalysis("""; this is a comment
                                            """)
        Assertions.assertEquals(result, listOf<Token>())
    }

    @Test
    fun commentAtEndOfInputWillNotBeIncludedInOutput() {
        val result = performLexicalAnalysis("""; this is a comment""")
        Assertions.assertEquals(result, listOf<Token>())
    }

    @Test
    fun whitespaceIsIgnored() {
        val result = performLexicalAnalysis("""

                                            
                                            3




                                                            """)
        Assertions.assertEquals(result.size, 1)
    }

}
