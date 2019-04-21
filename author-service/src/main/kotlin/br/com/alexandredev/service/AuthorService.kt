package br.com.alexandredev.service

import br.com.alexandredev.domain.Author
import br.com.alexandredev.repository.AuthorRepository
import org.springframework.stereotype.Service

@Service
class AuthorService(private var authorRepository: AuthorRepository) {

	fun save(author: Author): Author {
		return this.authorRepository.save(author)
	}

	fun findById(id: String): Author {
		return this.authorRepository.findById(id).orElse(null)
	}

	fun findAll(): List<Author> {
		return this.authorRepository.findAll().toList();
	}

	fun delete(id: String) {
		this.authorRepository.deleteById(id)
	}

}