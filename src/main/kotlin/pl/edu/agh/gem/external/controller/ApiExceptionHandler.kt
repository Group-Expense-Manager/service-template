package pl.edu.agh.gem.external.controller

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
    fun handleMissingProductException(exception: MissingProductException): ResponseEntity<ErrorsHolder> {
        return ResponseEntity(handleError(exception), NOT_FOUND)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(exception: MethodArgumentNotValidException): ResponseEntity<ErrorsHolder> {
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
        return SimpleErrorsHolder(errors)
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

interface Error

interface ErrorsHolder

data class SimpleErrorsHolder(val errors: List<Error>) : ErrorsHolder {
    companion object {
        fun fromError(error: Error) =
            SimpleErrorsHolder(listOf(error))
    }
}

data class SimpleError(
    private val code: String? = null,
    private val message: String? = null,
    private val details: String? = null,
    private val path: String? = null,
    private val userMessage: String? = null
) : Error {
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
