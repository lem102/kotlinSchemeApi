package com.example.testApi.parsing

class SExpressionList(val expressions: List<SExpression>) : SExpression {

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

