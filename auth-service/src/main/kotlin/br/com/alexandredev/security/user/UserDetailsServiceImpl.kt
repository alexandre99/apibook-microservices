package br.com.alexandredev.security.user

import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import br.com.alexandredev.domain.User
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UsernameNotFoundException

@Service
class UserDetailsServiceImpl : UserDetailsService {

	@Autowired
	lateinit var encoder: BCryptPasswordEncoder

	override fun loadUserByUsername(username: String): UserDetails? {

		val users: List<User> = listOf(
			User("1", "user", encoder.encode("12345"), "USER"),
			User("2", "admin", encoder.encode("12345"), "ADMIN")
		)

		users.forEach { user ->
			if (username.equals(user.userName)) {
				val grantedAuthorities: List<GrantedAuthority> =
					AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_" + user.role)
				return org.springframework.security.core.userdetails.User(
					user.userName,
					user.password,
					grantedAuthorities
				)
			}
		}
		throw UsernameNotFoundException("Username: " + username + " not found");
	}
}