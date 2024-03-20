package pl.edu.agh.gem.integration.ability

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.http.HttpHeaders
import org.springframework.http.HttpStatus.OK
import org.springframework.http.HttpStatusCode
import pl.edu.agh.gem.integration.environment.ProjectConfig.wiremock
import org.springframework.http.HttpHeaders.ACCEPT
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE

private const val EXAMPLE_URL = "/api/example"

private fun createUrl(id:String) =
        "$EXAMPLE_URL/$id"


fun stubExample(body:Any?,id:String,statusCode: HttpStatusCode = OK){
    wiremock.stubFor(
            post(createUrl(id))
                    .willReturn(
                            aResponse()
                                    .withStatus(statusCode.value())
                                    .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                                    .withHeader(ACCEPT, APPLICATION_JSON_VALUE)
                                    .withBody(
                                            jacksonObjectMapper().writeValueAsString(body)
                                    )
                    )
    )
}
