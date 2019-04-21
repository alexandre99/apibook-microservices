package br.com.alexandredev.handler

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import br.com.alexandredev.exception.AuthorException

@ControllerAdvice
class ResourceExceptionController {

	@ExceptionHandler(value = [(MethodArgumentNotValidException::class)])
	fun handlerFieldInvalid(e: MethodArgumentNotValidException): ResponseEntity<List<String?>> {
		val erros: List<String?> = e.bindingResult.fieldErrors.map { error -> error.defaultMessage }.toList();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
	}

	@ExceptionHandler(value = [(AuthorException::class)])
	fun handlerAuthorException(e: AuthorException): ResponseEntity<String> {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message);
	}

}