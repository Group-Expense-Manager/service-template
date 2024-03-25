package pl.edu.agh.gem.integration.assertion

import io.kotest.matchers.shouldBe
import org.springframework.http.HttpStatus
import org.springframework.test.web.reactive.server.WebTestClient

infix fun WebTestClient.ResponseSpec.shouldHaveHttpStatus(status: HttpStatus) {
    this.expectStatus().isEqualTo(status)
}

inline infix fun <reified T> WebTestClient.ResponseSpec.shouldHaveBody(expected: T) {
    this.expectBody(T::class.java).returnResult().responseBody shouldBe expected
}

infix fun WebTestClient.ResponseSpec.shouldHaveErrors(assertion: ErrorsResponse.() -> Unit) {
    this.expectBody(ErrorsResponse::class.java).returnResult().responseBody?.apply(assertion)
}

data class ErrorsResponse(
    val errors: List<ErrorJson>,
)

data class ErrorJson(
    val code: String? = null,
    val message: String? = null,
    val details: String? = null,
    val path: String? = null,
    val userMessage: String? = null,
)