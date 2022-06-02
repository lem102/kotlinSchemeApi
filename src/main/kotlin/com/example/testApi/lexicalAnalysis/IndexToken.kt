package com.example.testApi.lexicalAnalysis

data class IndexToken(val type: TokenType, val value: String, val startIndex: Int, val endIndex: Int) {
    constructor(protoToken: ProtoToken, type: TokenType, startIndex: Int, endIndex: Int = protoToken.endIndex)
        :this(type, protoToken.value, startIndex, endIndex)
}
