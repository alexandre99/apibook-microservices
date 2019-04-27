package br.com.alexandredev.service

import org.springframework.stereotype.Service
import br.com.alexandredev.security.token.JwtConfig
import io.jsonwebtoken.Jwts
import java.util.Date

@Service
class LogoutService(private val jwtConfig: JwtConfig) {

	fun logout(token: String) {
		val tokenWithoutBearer: String = token.replace(jwtConfig.prefix, "")
		Jwts.parser()
			.setSigningKey(jwtConfig.secret.toByteArray())
			.parseClaimsJws(tokenWithoutBearer)
			.getBody()
			.setExpiration(Date())
	}
}