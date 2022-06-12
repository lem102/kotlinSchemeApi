package com.example.testApi

fun skipToClosingParen(tokens: List<Token>): List<Token> {

    tailrec fun go(tokens: List<Token>, layersDeep: Int): List<Token> {
        val tail = tokens.drop(1)
        return when (tokens.firstOrNull()?.type) {
            null -> throw Exception("unmatched parentheses")
            TokenType.OPENING_PARENTHESIS -> go(tail, layersDeep + 1)
            TokenType.CLOSING_PARENTHESIS -> if (layersDeep == 0) tail else go(tail, layersDeep - 1)
            else -> go(tail, layersDeep)
        }
    }

    return go(tokens, 0)
}

fun areParensBalanced(tokens: List<Token>): Boolean {

    tailrec fun go(tokens: List<Token>, layersDeep: Int): Int {
        val tail = tokens.drop(1)
        return when (tokens.firstOrNull()?.type) {
            null -> layersDeep
            TokenType.OPENING_PARENTHESIS -> go(tail, layersDeep + 1)
            TokenType.CLOSING_PARENTHESIS -> go(tail, layersDeep - 1)            
            else -> go(tail, layersDeep)
        }
    }

    return go(tokens, 0) == 0 
}

fun performParsing(tokens: List<Token>): List<SExpression> {

    if (!areParensBalanced(tokens)) {
        throw Exception("unbalanced parentheses.")
    }
    
    tailrec fun go(tokens: List<Token>, sexps: List<SExpression>): List<SExpression> {
        val token = tokens.firstOrNull()
        val tail = tokens.drop(1)
        return when (token?.type) {
            null -> sexps
            TokenType.CLOSING_PARENTHESIS -> sexps
            TokenType.NUMBER -> go(tail, sexps + NumberAtom(token.value, token.start, token.end))
            TokenType.IDENTIFIER -> go(tail, sexps + IdentifierAtom(token.value, token.start, token.end))
            TokenType.STRING -> go(tail, sexps + StringAtom(token.value, token.start, token.end))
            TokenType.OPENING_PARENTHESIS -> go(skipToClosingParen(tail), sexps + SExpressionList(go(tail, listOf()), token.start, token.end))
            else -> throw Exception("invalid token")
        }
    }

    return go(tokens, listOf())
}
