package com.example.testApi

enum class TokenType {
    UNKOWN, OPENING_PARENTHESIS, CLOSING_PARENTHESIS, IDENTIFIER
}

enum class State {
    INITIAL_STATE, IDENTIFIER1, IDENTIFIER2
}

data class Position(var row: Int, var column: Int)

data class Token(val type: TokenType, val value: String, val startIndex: Int, val endIndex: Int)

fun indexToPosition(inputProgram: String, index: Int): Position {
    val inputBeforeIndex = inputProgram.subSequence(0, index)
    val analysedLines = inputBeforeIndex.lines()
    val numberOfLines = analysedLines.size
    return Position(numberOfLines, analysedLines.lastIndex)
}

fun readToken(inputProgram: String, startIndex: Int): Token {

    tailrec fun go(state: State, inputProgram: String, token: Token): Token {
        val nextIndex = token.endIndex + 1
        val newInputProgram = inputProgram.drop(1)
        val character = if (inputProgram.length > 0) inputProgram[0] else ' '
        return when (state) {
            State.INITIAL_STATE ->
                when {
                    character == '(' -> Token(TokenType.OPENING_PARENTHESIS, "", startIndex, nextIndex)
                    character == ')' -> Token(TokenType.CLOSING_PARENTHESIS, "", startIndex, nextIndex)
                    character.isLetter() -> go(State.IDENTIFIER1, newInputProgram, Token(token.type, character.toString(), startIndex, nextIndex))
                    else -> throw Exception("Failed to discern type of token at $token.startPosition")
                }
            State.IDENTIFIER1 ->
                when {
                    character.isLetter() -> go(State.IDENTIFIER1, newInputProgram, Token(token.type, token.value + character.toString(), startIndex, nextIndex))
                    character == '-' -> go(State.IDENTIFIER2, newInputProgram, Token(token.type, token.value + character.toString(), startIndex, nextIndex))
                    else -> Token(TokenType.IDENTIFIER, token.value, startIndex, token.endIndex)
                }
            State.IDENTIFIER2 ->
                when {
                    character.isLetter() -> go(State.IDENTIFIER1, newInputProgram, Token(token.type, token.value + character.toString(), startIndex, nextIndex))
                    else -> throw Exception("Error reading identifier at $token.startPosition")
                }
            
        }
    }

    return go(State.INITIAL_STATE, inputProgram, Token(TokenType.UNKOWN, "", startIndex, startIndex))
}

fun performLexicalAnalysis(inputProgram: String): List<Token> {

    tailrec fun go(inputProgram: String, index: Int, tokens: List<Token>): List<Token> {
        return if (inputProgram == "") tokens else {
            val token = readToken(inputProgram, index)
            go(inputProgram.substring(token.endIndex - index), token.endIndex, tokens + token)            
        }
    }

    return go(inputProgram, 0, listOf<Token>())
}

