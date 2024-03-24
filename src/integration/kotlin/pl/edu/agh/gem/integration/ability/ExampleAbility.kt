package pl.edu.agh.gem.integration.ability

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.post
import org.springframework.http.HttpHeaders.ACCEPT
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.HttpStatus.OK
import org.springframework.http.HttpStatusCode
import pl.edu.agh.gem.external.utils.InternalApiMediaType.APPLICATION_JSON_INTERNAL_VER_1
import pl.edu.agh.gem.integration.environment.ProjectConfig.wiremock

private const val EXAMPLE_URL = "/api/example"

private fun createUrl(id: String) =
    "$EXAMPLE_URL/$id"

private fun createUrlForAnyId() =
    "$EXAMPLE_URL/[^/]+"

fun stubExamplePostProduct(body: Any?, statusCode: HttpStatusCode = OK) {
    wiremock.stubFor(
        post(EXAMPLE_URL)
            .willReturn(
                aResponse()
                    .withStatus(statusCode.value())
                    .withHeader(CONTENT_TYPE, APPLICATION_JSON_INTERNAL_VER_1)
                    .withHeader(ACCEPT, APPLICATION_JSON_INTERNAL_VER_1)
                    .withBody(
                        jacksonObjectMapper().writeValueAsString(body)
                    )
            )
    )
}

fun stubExampleGetProduct(body: Any?, id: String, statusCode: HttpStatusCode = OK) {
    wiremock.stubFor(
        get(createUrl(id))
            .willReturn(
                aResponse()
                    .withStatus(statusCode.value())
                    .withHeader(CONTENT_TYPE, APPLICATION_JSON_INTERNAL_VER_1)
                    .withHeader(ACCEPT, APPLICATION_JSON_INTERNAL_VER_1)
                    .withBody(
                        jacksonObjectMapper().writeValueAsString(body)
                    )
            )
    )
}
