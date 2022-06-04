package  com.example.testApi.evaluation

import com.example.testApi.parsing.SExpression
import com.example.testApi.parsing.NumberAtom
import com.example.testApi.parsing.StringAtom
import com.example.testApi.parsing.IdentifierAtom

fun evaluate(sexps: List<SExpression>): List<Any> {

    tailrec fun go(sexps: List<SExpression>, results: List<Any>): List<Any> {
        return when (sexps.firstOrNull()) {
            null -> results
            is NumberAtom -> go(sexps.drop(1), results + (sexps[0] as NumberAtom).value)
            is StringAtom -> go(sexps.drop(1), results + (sexps[0] as StringAtom).value)
            is IdentifierAtom -> throw Exception("need to implement symbol table")
            else -> throw Exception()
        }
    }
    
    return go(sexps, listOf())
}

fun performEvaluation(sexps: List<SExpression>): List<Any> {
    // for every sexp in sexps
    // evaluate
    return evaluate(sexps)
}
