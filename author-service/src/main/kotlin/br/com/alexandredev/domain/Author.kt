package br.com.alexandredev.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.Date
import javax.validation.constraints.NotBlank

@Document(collection = "author")
data class Author(
	@Id
	var id: String?,
	@field:NotBlank(message = "The field name is empty")
	var name: String?,
	var nationality: String?,
	var dateBirth: Date?
) {
}