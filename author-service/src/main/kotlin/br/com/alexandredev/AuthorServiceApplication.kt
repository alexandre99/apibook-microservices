package br.com.alexandredev

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class AuthorServiceApplication

fun main(args: Array<String>) {
	runApplication<AuthorServiceApplication>(*args)
}
