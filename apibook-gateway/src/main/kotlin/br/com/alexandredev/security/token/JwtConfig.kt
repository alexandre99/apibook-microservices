package br.com.alexandredev.security

import org.springframework.beans.factory.annotation.Value

class JwtConfig {

	@Value("\${security.jwt.uri:/auth/**}")
	lateinit var uri: String

	@Value("\${security.jwt.header:Authorization}")
	lateinit var header: String

	@Value("\${security.jwt.prefix:Bearer }")
	lateinit var prefix: String

	@Value("\${security.jwt.expiration:#{24*60*60}}")
	lateinit var expiration: Number

	@Value("\${security.jwt.secret:JwtSecretKey}")
	lateinit var secret: String

}