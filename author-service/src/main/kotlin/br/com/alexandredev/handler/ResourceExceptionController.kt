package br.com.alexandredev.handler

import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import org.springframework.validation.BeanPropertyBindingResult
import org.springframework.validation.ObjectError

@ControllerAdvice
class ResourceExceptionController {

	@ExceptionHandler(value = [(MethodArgumentNotValidException::class)])
	fun handlerFieldInvalid(e: MethodArgumentNotValidException): ResponseEntity<List<String?>> {
		val erros: List<String?> = e.bindingResult.fieldErrors.map { error -> error.defaultMessage }.toList();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
	}

}