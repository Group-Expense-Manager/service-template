package pl.edu.agh.gem.external.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.core.Ordered.LOWEST_PRECEDENCE
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import pl.edu.agh.gem.internal.service.MissingProductException

@ControllerAdvice
@Order(LOWEST_PRECEDENCE)
class ApiExceptionHandler {

    @ExceptionHandler(MissingProductException::class)
    fun handleMissingProductException(exception: MissingProductException): ResponseEntity<SimpleErrorsHolder> {
        return ResponseEntity(handleError(exception), NOT_FOUND)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(exception: MethodArgumentNotValidException): ResponseEntity<SimpleErrorsHolder> {
        return ResponseEntity(handleNotValidException(exception), BAD_REQUEST)
    }

    private fun handleNotValidException(exception: MethodArgumentNotValidException): SimpleErrorsHolder {
        val errors = exception.bindingResult.fieldErrors
            .map { error ->
                SimpleError()
                    .withCode("VALIDATION_ERROR")
                    .withDetails(error.field)
                    .withUserMessage(error.defaultMessage)
                    .withMessage(error.defaultMessage)
            }
        return SimpleErrorsHolder(errors).apply {
            jacksonObjectMapper().writeValueAsString(this)
        }
    }

    private fun handleError(exception: Exception): SimpleErrorsHolder {
        val error = SimpleError()
            .withCode(exception.javaClass.simpleName)
            .withMessage(exception.message)
            .withDetails(exception.javaClass.simpleName)
            .withUserMessage(exception.message)
        return SimpleErrorsHolder.fromError(error)
    }
}
data class SimpleErrorsHolder(val errors: List<SimpleError>) {
    companion object {
        fun fromError(error: SimpleError) =
            SimpleErrorsHolder(listOf(error))
    }
}

data class SimpleError(
    val code: String? = null,
    val message: String? = null,
    val details: String? = null,
    val path: String? = null,
    val userMessage: String? = null,
) {
    fun withCode(code: String?) =
        this.copy(code = code)

    fun withMessage(message: String?) =
        this.copy(message = message)

    fun withDetails(details: String?) =
        this.copy(details = details)

    fun withPath(path: String?) =
        this.copy(path = path)

    fun withUserMessage(userMessage: String?) =
        this.copy(userMessage = userMessage)
}