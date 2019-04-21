package br.com.alexandredev.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.util.Date
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Document(collection = "book")
data class Book(
	@Id
	var id: String?,
	@field:NotBlank(message = "The field name is empty")
	var name: String?,
	@field:NotNull(message = "The field publication date is empty")
	var publicationDate: Date?,
	@field:NotBlank(message = "The field publishing company is empty")
	var publishingCompany: String?,
	@field:NotBlank(message = "The field brief is empty")
	var brief: String = "",
	@DBRef
	@field:NotNull(message = "The field author is null")
	val author: Author
) {
}