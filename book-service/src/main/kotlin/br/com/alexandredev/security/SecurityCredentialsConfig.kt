package br.com.alexandredev.security

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.context.annotation.Bean

@EnableWebSecurity
open class SecurityCredentialsConfig : WebSecurityConfigurerAdapter() {

	@Autowired
	lateinit var userDetailsService: UserDetailsService



	@Bean
	open fun passwordEncoder(): BCryptPasswordEncoder {
		return BCryptPasswordEncoder()
	}

}