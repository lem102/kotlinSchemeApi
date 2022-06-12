package com.example.testApi

data class SymbolAtom(val symbol: String, override val start: Position, override val end: Position) : Atom
