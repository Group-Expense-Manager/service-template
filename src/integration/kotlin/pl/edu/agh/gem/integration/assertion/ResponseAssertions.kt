package pl.edu.agh.gem.integration.assertion

import io.kotest.matchers.shouldBe
import org.springframework.http.HttpStatus
import org.springframework.test.web.reactive.server.WebTestClient
import pl.edu.agh.gem.external.controller.SimpleErrorsHolder

infix fun WebTestClient.ResponseSpec.shouldHaveHttpStatus(status: HttpStatus) {
    this.expectStatus().isEqualTo(status)
}

inline infix fun <reified T> WebTestClient.ResponseSpec.shouldHaveBody(expected: T) {
    this.expectBody(T::class.java).returnResult().responseBody shouldBe expected
}

infix fun WebTestClient.ResponseSpec.shouldHaveErrors(assertion: SimpleErrorsHolder.() -> Unit) {
    this.expectBody(SimpleErrorsHolder::class.java).returnResult().responseBody?.apply(assertion)
}