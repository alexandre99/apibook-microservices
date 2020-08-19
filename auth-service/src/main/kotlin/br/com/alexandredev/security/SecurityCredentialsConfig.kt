package br.com.alexandredev.security

import br.com.alexandredev.security.token.JwtConfig
import br.com.alexandredev.security.token.JwtTokenAuthenticationFilter
import br.com.alexandredev.security.token.JwtUsernameAndPasswordAuthenticationFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.http.HttpServletResponse

@EnableWebSecurity
open class SecurityCredentialsConfig : WebSecurityConfigurerAdapter() {

	@Autowired
	lateinit var userDetailsService: UserDetailsService

	@Autowired
	lateinit var jwtConfig: JwtConfig

	override fun configure(http: HttpSecurity) {
		http
			.csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				.exceptionHandling().authenticationEntryPoint{_, resp,_ -> resp.sendError(HttpServletResponse.SC_UNAUTHORIZED)}
			.and()
			    .addFilter(JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig))
				.addFilterAfter(JwtTokenAuthenticationFilter(jwtConfig), UsernamePasswordAuthenticationFilter::class.java)
		    .authorizeRequests()
			    .antMatchers(HttpMethod.POST, jwtConfig.uri).permitAll()
			    .anyRequest().authenticated()
	}

	override protected fun configure(auth: AuthenticationManagerBuilder) {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	open fun passwordEncoder(): BCryptPasswordEncoder {
		return BCryptPasswordEncoder()
	}

}