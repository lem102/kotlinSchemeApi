package com.example.testApi

data class ClosureAtom(val function: Function,
                       val lexicalEnvironment : Map<String, Any>,
                       override val start: Position,
                       override val end: Position): Atom
