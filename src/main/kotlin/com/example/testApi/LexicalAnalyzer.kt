package com.example.testApi

import com.example.testApi.Token

fun performLexicalAnalysis(inputProgram: String): List<Token> {
    return listOf(Token("identifier", "apple", 0, 0), Token("identifier", "banana", 5, 0))
}
