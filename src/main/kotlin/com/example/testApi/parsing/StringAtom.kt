package com.example.testApi.parsing

import com.example.testApi.lexicalAnalysis.Position

class StringAtom(val value: String, override val start: Position, override val end: Position) : Atom {

    override fun toString(): String {
        return "StringAtom($value)"
    }
}
