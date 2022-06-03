package com.example.testApi

import com.example.testApi.lexicalAnalysis.performLexicalAnalysis
import com.example.testApi.lexicalAnalysis.Token
import com.example.testApi.parsing.performParsing
import com.example.testApi.parsing.SExpression

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import java.time.LocalDateTime

data class StatusObject(val timestamp:LocalDateTime, val message:String) {
    constructor():this(LocalDateTime.now(), "Server Ok!")
}

data class InputProgram(val sourceCode:String)

@RestController
@RequestMapping("/")
class LexerController {
    
    @GetMapping("status")
    fun getStatus():ResponseEntity<StatusObject> {
        return ResponseEntity(StatusObject(), HttpStatus.OK)
    }

    @PostMapping("lexer")
    fun performLexicalAnalysis(@RequestBody inputProgram: InputProgram):ResponseEntity<List<Token>> {
        val output = inputProgram.sourceCode
            .let { performLexicalAnalysis(it) }
        return ResponseEntity(output, HttpStatus.OK)
    }

    @PostMapping("parser")
    fun performParsing(@RequestBody inputProgram: InputProgram):ResponseEntity<List<SExpression>> {
        val output = inputProgram.sourceCode
            .let { performLexicalAnalysis(it) }
            .let { performParsing(it) }
        return ResponseEntity(output, HttpStatus.OK)
    }
}
