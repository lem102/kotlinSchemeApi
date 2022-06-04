package com.example.testApi.parsing

import com.example.testApi.lexicalAnalysis.Position

class IdentifierAtom(val identifier: String, override val start: Position, override val end: Position) : Atom {
    override fun toString(): String {
        return "IdentifierAtom($identifier)"
    }
}
