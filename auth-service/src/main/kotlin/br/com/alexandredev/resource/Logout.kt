package br.com.alexandredev.resource

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestBody
import javax.validation.constraints.NotNull
import org.springframework.web.bind.annotation.RequestHeader
import javax.validation.constraints.NotBlank
import org.springframework.http.ResponseEntity
import br.com.alexandredev.service.LogoutService
import org.springframework.web.bind.annotation.PostMapping

@RestController
@RequestMapping("/auth")
class Logout(private val logoutService: LogoutService) {

	@PostMapping("/logout")
	fun logout(@RequestHeader(value = "Authorization") @NotBlank token: String): ResponseEntity<Unit> {
		this.logoutService.logout(token)
		return ResponseEntity.noContent().build()
	}
}