package  com.example.testApi.evaluation

import com.example.testApi.parsing.SExpression
import com.example.testApi.parsing.NumberAtom
import com.example.testApi.parsing.StringAtom
import com.example.testApi.parsing.IdentifierAtom
import com.example.testApi.parsing.SExpressionList

val symbolTable = mapOf<String, Any>("add" to { x: Int, y: Int -> x + y },
                                     "subtract" to { x: Int, y: Int -> x - y })

fun performEvaluation(sexps: List<SExpression>): List<Any> {

    tailrec fun go(sexps: List<SExpression>, results: List<Any>): List<Any> {
        val sexp = sexps.firstOrNull()
        val tail = sexps.drop(1)
        return when (sexp) {
            null -> results
            is NumberAtom -> go(tail, results + sexp.value)
            is StringAtom -> go(tail, results + sexp.value)
            // need to implement symbol table that can be searched
            is IdentifierAtom -> go(tail, results + symbolTable.getValue(sexp.identifier))
            // most complex case. evaluate all the expressions, then work out the result of the function call
            is SExpressionList -> throw Exception("not implemented")
            else -> throw Exception()
        }
    }
    
    return go(sexps, listOf())
}

