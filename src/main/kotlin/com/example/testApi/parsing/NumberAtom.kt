package com.example.testApi.parsing

import com.example.testApi.lexicalAnalysis.Position

class NumberAtom(val value: Float, override val start: Position, override val end: Position) : Atom {

    constructor(value: String, start : Position, end : Position):this(value.toFloat(), start, end)

    override fun toString(): String {
        return "NumberAtom($value) at row $start.row, $start.column"
    }
}
