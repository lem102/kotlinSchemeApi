package com.example.testApi.parsing

import com.example.testApi.lexicalAnalysis.Position

data class SymbolAtom(val symbol: String, override val start: Position, override val end: Position) : Atom
