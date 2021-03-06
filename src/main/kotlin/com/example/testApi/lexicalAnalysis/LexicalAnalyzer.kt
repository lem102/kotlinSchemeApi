package com.example.testApi

fun Char.isUnicodeNullOrLineEnd(): Boolean =
    this == '\u9216' || this.toString().matches(Regex("\\R"))

fun indexToPosition(inputProgram: String, index: Int): Position {
    val inputBeforeIndex = inputProgram.subSequence(0, index)
    val analysedLines = inputBeforeIndex.lines()
    val numberOfLines = analysedLines.size
    return Position(numberOfLines, analysedLines.last().length)
}

fun performLexicalAnalysis(inputProgram: String): List<Token> {

    fun readToken(startIndex: Int): IndexToken {

        fun nextCharacter(inputProgram: String, index: Int): Char =
            try {inputProgram[index]} catch(e: Exception) { '\u9216' }

        tailrec fun go(state: State, token: ProtoToken): IndexToken {
            val nextIndex = token.end + 1
            val character = nextCharacter(inputProgram, token.end)
            return when (state) {
                State.INITIAL_STATE ->
                    when {
                        character == '\u9216' -> IndexToken(token, TokenType.WHITESPACE, startIndex, nextIndex)
                        character == '(' -> IndexToken(token, TokenType.OPENING_PARENTHESIS, startIndex, nextIndex)
                        character == ')' -> IndexToken(token, TokenType.CLOSING_PARENTHESIS, startIndex, nextIndex)
                        character == ';' -> go(State.COMMENT, ProtoToken("", nextIndex))
                        character == '"' -> go(State.STRING1, ProtoToken(character.toString(), nextIndex))
                        character.isLetter() -> go(State.IDENTIFIER1, ProtoToken(character.toString(), nextIndex))
                        character.isWhitespace() -> IndexToken(token, TokenType.WHITESPACE, startIndex, nextIndex)
                        character.isDigit() -> go(State.NUMBER1, ProtoToken(character.toString(), nextIndex))
                        else -> throw Exception("Failed to discern type of token at $token.startPosition")
                    }
                State.COMMENT -> 
                    when {
                        character.isUnicodeNullOrLineEnd() -> IndexToken(token, TokenType.COMMENT, startIndex, nextIndex)
                        else -> go(State.COMMENT, ProtoToken("", nextIndex))
                    }
                State.IDENTIFIER1 ->
                    when {
                        character == '\u9216' -> IndexToken(token, TokenType.IDENTIFIER, startIndex)
                        character.isLetter() -> go(State.IDENTIFIER1, ProtoToken(token.value + character, nextIndex))
                        character == '-' -> go(State.IDENTIFIER2, ProtoToken(token.value + character, nextIndex))
                        else -> IndexToken(token, TokenType.IDENTIFIER, startIndex)
                    }
                State.IDENTIFIER2 ->
                    when {
                        character.isLetter() -> go(State.IDENTIFIER1, ProtoToken(token.value + character, nextIndex))
                        else -> throw Exception("Error reading identifier at $token.startPosition")
                    }
                State.NUMBER1 ->
                    when {
                        character.isDigit() -> go(State.NUMBER1, ProtoToken(token.value + character, nextIndex))
                        character == '.' -> go(State.NUMBER2, ProtoToken(token.value + character, nextIndex))
                        else -> IndexToken(token, TokenType.NUMBER, startIndex)
                    }
                State.NUMBER2 ->
                    when {
                        character.isDigit() -> go(State.NUMBER3, ProtoToken(token.value + character, nextIndex))
                        else -> throw Exception("Error reading number at $token.startPosition")
                    }
                State.NUMBER3 ->
                    when {
                        character.isDigit() -> go(State.NUMBER3, ProtoToken(token.value + character, nextIndex))
                        else -> IndexToken(token, TokenType.NUMBER, startIndex)
                    }
                State.STRING1 ->
                    when {
                        character == '\\' -> go(State.STRING2, ProtoToken(token.value + character, nextIndex))
                        character == '"' -> go(State.STRING3, ProtoToken(token.value + character, nextIndex))
                        else -> go(State.STRING1, ProtoToken(token.value + character, nextIndex))
                    }
                State.STRING2 -> go(State.STRING1, ProtoToken(token.value + character, nextIndex))
                State.STRING3 -> IndexToken(token, TokenType.STRING, startIndex)
            }
        }

        return go(State.INITIAL_STATE, ProtoToken("", startIndex))
    }

    tailrec fun go(index: Int, tokens: List<IndexToken>): List<IndexToken> {
        return when (inputProgram.length < index) {
            true -> tokens
            else -> {
                val token = readToken(index)
                go(token.end, tokens + token)
            }
        }
    }

    return go(0, listOf())
        .filterNot { setOf(TokenType.WHITESPACE, TokenType.COMMENT).contains(it.type) }
        .map { Token(inputProgram, it) }
}

