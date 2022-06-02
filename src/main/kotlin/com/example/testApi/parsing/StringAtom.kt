package com.example.testApi.parsing

class StringAtom(string: String) : Atom {
    val value = string.drop(1).dropLast(1)

    override fun toString(): String {
        return "StringAtom($value)"
    }
}
