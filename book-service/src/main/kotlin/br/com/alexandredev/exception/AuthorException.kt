package br.com.alexandredev.exception

import java.lang.RuntimeException

class AuthorException(private val msg: String) : RuntimeException(msg) {
}