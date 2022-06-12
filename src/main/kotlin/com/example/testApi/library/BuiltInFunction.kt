package com.example.testApi

data class BuiltInFunction(override val name : String,
                           val function : (List<SExpression>) -> SExpression) : Function
