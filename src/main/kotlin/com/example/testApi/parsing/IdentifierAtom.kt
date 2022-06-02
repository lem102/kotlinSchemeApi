package com.example.testApi.parsing

class IdentifierAtom(val identifier: String) : Atom {
    override fun toString(): String {
        return "IdentifierAtom($identifier)"
    }
}
