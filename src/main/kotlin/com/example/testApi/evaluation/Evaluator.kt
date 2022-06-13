package com.example.testApi

val addClosure = ClosureAtom(BuiltInFunction("add",
                                         fun (numbers: List<SExpression>): NumberAtom {

                                             if (numbers.size != 2) {
                                                 throw Exception("wrong number of arguments")
                                             }

                                             val firstNumber = if (numbers[0] is NumberAtom) {
                                                 numbers[0] as NumberAtom
                                             } else {
                                                 throw Exception("First argument is not a number")
                                             }
                                             
                                             val secondNumber = if (numbers[1] is NumberAtom) {
                                                 numbers[1] as NumberAtom
                                             } else {
                                                 throw Exception("Second argument is not a number")
                                             }

                                             return NumberAtom(firstNumber.value + secondNumber.value, firstNumber.start, firstNumber.end)
                                         }
                         ),
                         mapOf(),
                         Position(0, 0),
                         Position(0, 0))

val lexicalEnvironment = mapOf<String, ClosureAtom>("add" to addClosure)

fun callFunction(sExpressions: List<SExpression>): SExpression {
    val closure = performEvaluation(listOf(sExpressions[0]))[0] as ClosureAtom
    val arg1 = performEvaluation(listOf(sExpressions[1]))[0]
    val arg2 = performEvaluation(listOf(sExpressions[2]))[0]
    val builtin = closure.function as BuiltInFunction
    return builtin.function(listOf(arg1, arg2))
}

fun performEvaluation(sexps: List<SExpression>): List<SExpression> {

    tailrec fun go(sexps: List<SExpression>, results: List<SExpression>): List<SExpression> {
        val sexp = sexps.firstOrNull()
        val tail = sexps.drop(1)
        return when (sexp) {
            null -> results
            is NumberAtom -> go(tail, results + sexp)
            is StringAtom -> go(tail, results + sexp)
            // need to implement symbol table that can be searched
            is IdentifierAtom -> go(tail, results + lexicalEnvironment.getValue(sexp.identifier))
            // most complex case. evaluate all the expressions, then work out the result of the function call
            is SExpressionList -> go(tail, results + callFunction(sexp.expressions))
            else -> throw Exception("unhandled SExpression")
        }
    }
    
    return go(sexps, listOf())
}

