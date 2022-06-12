package com.example.testApi

class StringAtom(val value: String, override val start: Position, override val end: Position) : Atom {

    override fun toString(): String {
        return "StringAtom($value)"
    }
}
