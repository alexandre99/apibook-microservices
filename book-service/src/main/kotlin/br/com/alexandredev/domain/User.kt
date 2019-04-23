package br.com.alexandredev.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "user")
class User(
	val id: String?,
	val userName: String?,
	val password: String?,
	val role: String?
) {
}