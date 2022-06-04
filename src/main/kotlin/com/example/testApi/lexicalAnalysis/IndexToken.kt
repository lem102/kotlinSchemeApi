package com.example.testApi.lexicalAnalysis

data class IndexToken(val type: TokenType, val value: String, val start: Int, val end: Int) {
    constructor(protoToken: ProtoToken, type: TokenType, start: Int, end: Int = protoToken.end)
        :this(type, protoToken.value, start, end)
}
