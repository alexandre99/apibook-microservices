package br.com.alexandredev.domain

import org.springframework.data.mongodb.core.mapping.Document
import javax.validation.constraints.NotBlank

@Document(collection = "author")
data class Author(
	@field:NotBlank
	val id: String = "",
	val name: String = ""
) {
	val isInvalid = { this.id.isEmpty() }
}