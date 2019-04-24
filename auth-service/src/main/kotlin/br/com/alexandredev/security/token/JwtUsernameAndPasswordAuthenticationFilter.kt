package br.com.alexandredev.security.token

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import java.io.IOException
import java.util.Collections
import java.util.stream.Collectors
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.util.Date
import java.time.LocalDateTime
import java.time.Instant
import java.util.TimeZone
import br.com.alexandredev.domain.UserCredentials

class JwtUsernameAndPasswordAuthenticationFilter(
	var authManager: AuthenticationManager,
	var jwtConfig: JwtConfig
) : UsernamePasswordAuthenticationFilter() {
	// We use auth manager to validate the user credentials

	init {
		// By default, UsernamePasswordAuthenticationFilter listens to "/login" path.
		// In our case, we use "/auth". So, we need to override the defaults.
		setRequiresAuthenticationRequestMatcher(AntPathRequestMatcher(jwtConfig.uri, "POST"))
	}

	override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
		try {
			// 1. Get credentials from request
			val creds: UserCredentials =
				ObjectMapper().readValue(request.getInputStream(), UserCredentials::class.java)

			// 2. Create auth object (contains credentials) which will be used by auth manager
			val authToken: UsernamePasswordAuthenticationToken =
				UsernamePasswordAuthenticationToken(creds.userName, creds.password, Collections.emptyList())

			// 3. Authentication manager authenticate the user, and use UserDetialsServiceImpl::loadUserByUsername() method to load the user.
			return authManager.authenticate(authToken)
		} catch (e: IOException) {
			throw RuntimeException(e);
		}
	}

	override fun successfulAuthentication(
		request: HttpServletRequest,
		response: HttpServletResponse,
		chain: FilterChain,
		auth: Authentication
	) {
		val now: Number = System.currentTimeMillis()
		val token: String = Jwts.builder()
								.setSubject(auth.name)
								// Convert to list of strings.
								// This is important because it affects the way we get them back in the Gateway.
								.claim(
									"authorities", auth.getAuthorities().map(GrantedAuthority::getAuthority).toList()
								)
								.setIssuedAt(Date(now.toLong()))
								.setExpiration(Date(now.toLong() + jwtConfig.expiration.toLong() * 1000))  // in milliseconds
								.signWith(SignatureAlgorithm.HS512, jwtConfig.secret.toByteArray())
								.compact();
		// Add token to header
		response.addHeader(jwtConfig.header, jwtConfig.prefix + token);
	}

}