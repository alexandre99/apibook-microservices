package br.com.alexandredev.security

import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import br.com.alexandredev.security.JwtConfig
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import javax.servlet.http.HttpServletResponse
import br.com.alexandredev.security.token.JwtTokenAuthenticationFilter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.http.HttpMethod
import org.springframework.beans.factory.annotation.Autowired

@EnableWebSecurity
open class SecurityTokenConfig : WebSecurityConfigurerAdapter() {

	@Autowired
	lateinit var jwtConfig: JwtConfig

	override fun configure(http: HttpSecurity) {
		http.csrf().disable()
				// make sure we use stateless session; session won't be used to store user's state.
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				// handle an authorized attempts
				.exceptionHandling().authenticationEntryPoint{_, resp,_ -> resp.sendError(HttpServletResponse.SC_UNAUTHORIZED)}
			.and()
				// Add a filter to validate the tokens with every request
				.addFilterAfter(JwtTokenAuthenticationFilter(jwtConfig), UsernamePasswordAuthenticationFilter::class.java)
			.authorizeRequests()
				// allow all who are accessing "auth" service
				.antMatchers(HttpMethod.POST, jwtConfig.uri).permitAll()
				// must be an admin if trying to access admin area (authentication is also required here)
//				.antMatchers("/gallery" + "/admin/**").hasRole("ADMIN")
				// Any other request must be authenticated
				.anyRequest().authenticated();
	}

	@Bean
	open fun jwtConfig(): JwtConfig {
		return JwtConfig()
	}
}