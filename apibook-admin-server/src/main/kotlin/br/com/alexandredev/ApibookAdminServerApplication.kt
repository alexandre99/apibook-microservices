package br.com.alexandredev.apibookadminserver

import de.codecentric.boot.admin.server.config.AdminServerProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import de.codecentric.boot.admin.server.config.EnableAdminServer

@EnableAdminServer
@SpringBootApplication
open class ApibookAdminServerApplication(private val adminServerProperties: AdminServerProperties) {

	private var adminContextPath: String

	init {
		this.adminContextPath = adminServerProperties.getContextPath();
	}

	@Bean
	open fun securityWebFilterChainSecure(http: ServerHttpSecurity): SecurityWebFilterChain {
		return http.authorizeExchange()
			.pathMatchers(adminContextPath + "/assets/**").permitAll()
			.pathMatchers(adminContextPath + "/login").permitAll()
			.anyExchange().authenticated()
			.and().formLogin().loginPage(adminContextPath + "/login")
			.and().logout().logoutUrl(adminContextPath + "/logout")
			.and().httpBasic()
			.and().csrf().disable().build();
	}

}

fun main(args: Array<String>) {
	runApplication<ApibookAdminServerApplication>(*args)
}



