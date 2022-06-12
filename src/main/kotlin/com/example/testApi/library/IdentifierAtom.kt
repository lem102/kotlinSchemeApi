package com.example.testApi

class IdentifierAtom(val identifier: String, override val start: Position, override val end: Position) : Atom {
    override fun toString(): String {
        return "IdentifierAtom($identifier)"
    }
}
