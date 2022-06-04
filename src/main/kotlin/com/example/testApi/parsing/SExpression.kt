package com.example.testApi.parsing

import com.example.testApi.lexicalAnalysis.Position

interface SExpression {
    val start: Position
    val end: Position
}
