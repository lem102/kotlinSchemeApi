package com.example.testApi

class SExpressionList(val expressions: List<SExpression>, override val start: Position, override val end: Position) : SExpression {

    override fun toString(): String {

        tailrec fun go(string: String): String {
            val expression = expressions.firstOrNull()
            return when (expression) {
                null -> string
                else -> go(string + expression.toString())
            }
        }
        
        return go("")
    }
}

