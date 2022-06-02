package com.example.testApi.lexicalAnalysis

data class Token(val type: TokenType, val value: String, val startIndex: Position, val endIndex: Position) {
    constructor(inputProgram: String, indexToken: IndexToken)
        :this(indexToken.type, indexToken.value, indexToPosition(inputProgram, indexToken.startIndex), indexToPosition(inputProgram, indexToken.endIndex))
}

