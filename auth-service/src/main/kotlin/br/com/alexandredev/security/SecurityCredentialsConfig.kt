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
				// make sure we use stateless session; session won't be used to store user's state.
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				// handle an authorized attempts
				.exceptionHandling().authenticationEntryPoint{_, resp,_ -> resp.sendError(HttpServletResponse.SC_UNAUTHORIZED)}
			.and()
				 // Add a filter to validate user credentials and add token in the response header
			    // What's the authenticationManager()?
			    // An object provided by WebSecurityConfigurerAdapter, used to authenticate the user passing user's credentials
			    // The filter needs this auth manager to authenticate the user.
			    .addFilter(JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig))
				.addFilterAfter(JwtTokenAuthenticationFilter(jwtConfig), UsernamePasswordAuthenticationFilter::class.java)
		    .authorizeRequests()
			    // allow all POST requests
			    .antMatchers(HttpMethod.POST, jwtConfig.uri).permitAll()
//			    .antMatchers(HttpMethod.POST, "/auth/logout").permitAll()
			    // any other requests must be authenticated
			    .anyRequest().authenticated()
	}

	// Spring has UserDetailsService interface, which can be overriden to provide our implementation for fetching user from database (or any other source).
	// The UserDetailsService object is used by the auth manager to load the user from database.
	// In addition, we need to define the password encoder also. So, auth manager can compare and verify passwords.
	override protected fun configure(auth: AuthenticationManagerBuilder) {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	open fun passwordEncoder(): BCryptPasswordEncoder {
		return BCryptPasswordEncoder()
	}

}