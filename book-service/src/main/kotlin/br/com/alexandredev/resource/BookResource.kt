package br.com.alexandredev

import br.com.alexandredev.domain.Book
import br.com.alexandredev.service.BookService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping("book")
class BookResource(private val bookService: BookService) {

	@PostMapping
	fun save(@Validated @RequestBody book: Book): ResponseEntity<Book> {
		this.bookService.save(book);
		return ResponseEntity.status(HttpStatus.CREATED).build()
	}

	@PutMapping
	fun update(@Validated @RequestBody book: Book): ResponseEntity<Unit> {
		this.bookService.save(book);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	fun findById(@PathVariable("id") id: String): ResponseEntity<Book> {
		return ResponseEntity.status(HttpStatus.OK).body(this.bookService.findById(id));
	}

	@GetMapping
	fun findAll(): ResponseEntity<List<Book>> {
		return ResponseEntity.status(HttpStatus.OK).body(this.bookService.findAll());
	}

	@DeleteMapping("/{id}")
	fun delete(@PathVariable("id") id: String): ResponseEntity<Unit> {
		this.bookService.delete(id);
		return ResponseEntity.noContent().build();
	}

}