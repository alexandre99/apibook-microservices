package br.com.alexandredev.service

import br.com.alexandredev.domain.Book
import br.com.alexandredev.repository.BookRepository
import org.springframework.stereotype.Service
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.Exception
import br.com.alexandredev.domain.Author
import br.com.alexandredev.exception.AuthorException

@Service
class BookService(private var bookRepository: BookRepository) {

	private val logger: Logger = LoggerFactory.getLogger(javaClass);

	fun save(book: Book): Book {
		checkAuthor(book.author)
		return this.bookRepository.save(book)
	}

	fun findById(id: String): Book? {
		return this.bookRepository.findById(id).orElse(null)
	}

	fun findAll(): List<Book> {
		return this.bookRepository.findAll().toList()
	}

	fun delete(id: String) {
		this.bookRepository.deleteById(id)
	}

	private fun checkAuthor(author: Author) {
		if (author.isInvalid()) {
			val msg: String = "Author invalid"
			logger.error(msg)
			throw AuthorException(msg)
		}
	}

}