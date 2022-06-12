package com.example.testApi

data class Token(val type: TokenType, val value: String, val start: Position, val end: Position) {
    constructor(inputProgram: String, indexToken: IndexToken)
        :this(indexToken.type, indexToken.value, indexToPosition(inputProgram, indexToken.start), indexToPosition(inputProgram, indexToken.end))
}

