package br.com.alexandredev.security.token

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Bean

@Configuration
open class JwtConfigProvided {

	@Bean
	open fun jwtConfig(): JwtConfig {
		return JwtConfig()
	}

}