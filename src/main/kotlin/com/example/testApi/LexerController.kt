package com.example.testApi

import com.example.testApi.performLexicalAnalysis

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
        val output = performLexicalAnalysis(inputProgram.sourceCode)
        return ResponseEntity(output, HttpStatus.OK)
    }
}
