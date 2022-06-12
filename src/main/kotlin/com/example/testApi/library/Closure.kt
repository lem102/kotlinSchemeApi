package com.example.testApi

data class Closure(val function: Function,
                   val lexicalEnvironment : Map<String, Any>,
                   val start: Position,
                   val end: Position)
