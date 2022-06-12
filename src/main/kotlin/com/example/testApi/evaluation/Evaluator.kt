package com.example.testApi

val addClosure = Closure(BuiltInFunction("add",
                                         fun (numbers: List<NumberAtom>): NumberAtom {
                                             return NumberAtom(numbers[0].value + numbers[1].value,
                                                               numbers[0],
                                                               numbers[1].end)
                                         }
                         ),
                         mapOf(),
                         Position(0, 0),
                         Position(0, 0))

val lexicalEnvironment = mapOf<String, Closure>("add" to addClosure)

fun performEvaluation(sexps: List<SExpression>): List<Any> {

    tailrec fun go(sexps: List<SExpression>, results: List<Any>): List<Any> {
        val sexp = sexps.firstOrNull()
        val tail = sexps.drop(1)
        return when (sexp) {
            null -> results
            is NumberAtom -> go(tail, results + sexp)
            is StringAtom -> go(tail, results + sexp)
            // need to implement symbol table that can be searched
            is IdentifierAtom -> go(tail, results + lexicalEnvironment.getValue(sexp.identifier))
            // most complex case. evaluate all the expressions, then work out the result of the function call
            // is SExpressionList -> go(tail, results + callFunction(sexp.expressions))
            else -> throw Exception("unhandled SExpression")
        }
    }
    
    return go(sexps, listOf())
}

