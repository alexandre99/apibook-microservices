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
import org.slf4j.LoggerFactory
import org.slf4j.Logger
import java.util.Date

class JwtTokenAuthenticationFilter(private var jwtConfig: JwtConfig) : OncePerRequestFilter() {

	private val log: Logger = LoggerFactory.getLogger(javaClass);

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

		val token: String = header.replace(jwtConfig.prefix, "")

		try {
			val claims: Claims = Jwts.parser()
									.setSigningKey(jwtConfig.secret.toByteArray())
									.parseClaimsJws(token)
									.getBody()

			val userName: String? = claims.getSubject()
			if (Objects.nonNull(userName) && claims.getExpiration().after(Date())) {
				val roles: List<SimpleGrantedAuthority> =
								(claims.get("authorities") as List<String>).map { authoritie -> SimpleGrantedAuthority(authoritie) }
									.toList()
				val auth: UsernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(userName, null, roles)
				SecurityContextHolder.getContext().setAuthentication(auth);

			}
		} catch (e: Exception) {
			log.error("User not found: ${e.message}")
			SecurityContextHolder.clearContext();
		}
		chain.doFilter(request, response);
	}
}