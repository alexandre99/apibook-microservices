package br.com.alexandredev.resource

import br.com.alexandredev.domain.Author
import br.com.alexandredev.service.AuthorService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("author")
class AuthorResource(private var authorService: AuthorService) {

	@PostMapping
	fun save(@Valid @RequestBody author: Author): ResponseEntity<Author> {
		this.authorService.save(author);
		return ResponseEntity.status(HttpStatus.CREATED).build()
	}

	@PutMapping
	fun update(@Valid @RequestBody author: Author): ResponseEntity<Unit> {
		this.authorService.save(author);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	fun findById(@PathVariable("id") id: String): ResponseEntity<Author> {
		return ResponseEntity.status(HttpStatus.OK).body(this.authorService.findById(id));
	}

	@GetMapping
	fun findAll(): ResponseEntity<List<Author>> {
		return ResponseEntity.status(HttpStatus.OK).body(this.authorService.findAll());
	}

	@DeleteMapping("/{id}")
	fun delete(@PathVariable("id") id: String): ResponseEntity<Unit> {
		this.authorService.delete(id);
		return ResponseEntity.noContent().build();
	}

}