package br.com.alexandredev.security.token

import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.FilterChain
import br.com.alexandredev.security.JwtConfig
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import java.util.Objects
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder

class JwtTokenAuthenticationFilter(private var jwtConfig: JwtConfig) : OncePerRequestFilter() {

	override fun doFilterInternal(
		request: HttpServletRequest,
		response: HttpServletResponse,
		chain: FilterChain
	) {
		val header: String? = request.getHeader(jwtConfig.header);

		if (header == null || !header.startsWith(jwtConfig.prefix)) {
			// If not valid, go to the next filter.
			chain.doFilter(request, response)
			return;
		}

		// If there is no token provided and hence the user won't be authenticated.
		// It's Ok. Maybe the user accessing a public path or asking for a token.

		// All secured paths that needs a token are already defined and secured in config class.
		// And If user tried to access without access token, then he won't be authenticated and an exception will be thrown.

		// 3. Get the token

		val token: String = header.replace(jwtConfig.prefix, "")

		try {
			val claims: Claims = Jwts.parser()
									.setSigningKey(jwtConfig.secret.toByteArray())
									.parseClaimsJws(token)
									.getBody()
			val userName: String? = claims.getSubject()
			if (Objects.nonNull(userName)) {
				// 5. Create auth object
				// UsernamePasswordAuthenticationToken: A built-in object, used by spring to represent the current authenticated / being authenticated user.
				// It needs a list of authorities, which has type of GrantedAuthority interface, where SimpleGrantedAuthority is an implementation of that interface
				val roles: List<SimpleGrantedAuthority> =
								(claims.get("authorities") as List<String>).map { authoritie -> SimpleGrantedAuthority(authoritie) }
									.toList()
				val auth: UsernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(userName, null, roles)

				 // 6. Authenticate the user
				 // Now, user is authenticated
				 SecurityContextHolder.getContext().setAuthentication(auth);

			}
		} catch (e: Exception) {
			// In case of failure. Make sure it's clear; so guarantee user won't be authenticated
			SecurityContextHolder.clearContext();
		}

		// go to the next filter in the filter chain
		chain.doFilter(request, response);

	}
}