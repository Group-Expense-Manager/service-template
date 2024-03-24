package pl.edu.agh.gem.integration.ability

import org.springframework.context.annotation.Lazy
import org.springframework.http.HttpHeaders.ACCEPT
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.stereotype.Component
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec
import org.springframework.test.web.servlet.client.MockMvcWebTestClient
import org.springframework.web.context.WebApplicationContext
import java.net.URI
@Component
@Lazy
class ServiceTestClient(applicationContext: WebApplicationContext) {
    private val webClient = MockMvcWebTestClient.bindToApplicationContext(applicationContext)
        .configureClient()
        .build()

    fun exampleSth(body: ExampleRequest): ResponseSpec {
        return webClient.post()
            .uri(URI("/resources/foos"))
            .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
            .header(ACCEPT, APPLICATION_JSON_VALUE)
            .bodyValue(body)
            .exchange()
    }
}
