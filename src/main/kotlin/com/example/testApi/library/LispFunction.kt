package com.example.testApi

data class LispFunction(override val name : String,
                        val SExpression : SExpression) : Function
