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

		// hard coding the users. All passwords must be encoded.
		val users: List<User> = listOf(
			User("1", "user", encoder.encode("12345"), "USER"),
			User("2", "admin", encoder.encode("12345"), "ADMIN")
		)
		users.forEach { user ->
			if (username.equals(user.userName)) {
				// Remember that Spring needs roles to be in this format: "ROLE_" + userRole (i.e. "ROLE_ADMIN")
				// So, we need to set it to that format, so we can verify and compare roles (i.e. hasRole("ADMIN")).
				val grantedAuthorities: List<GrantedAuthority> =
					AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_" + user.role)

				// The "User" class is provided by Spring and represents a model class for user to be returned by UserDetailsService
				// And used by auth manager to verify and check user authentication.
				return org.springframework.security.core.userdetails.User(
					user.userName,
					user.password,
					grantedAuthorities
				)
			}
		}
		// If user not found. Throw this exception.
		throw UsernameNotFoundException("Username: " + username + " not found");
	}
}