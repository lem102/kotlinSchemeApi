package com.example.testApi.parsing

class NumberAtom(val value: Float) : Atom {

    constructor(value: String):this(value.toFloat())
    
    override fun toString(): String {
        return "NumberAtom($value)"
    }
}
