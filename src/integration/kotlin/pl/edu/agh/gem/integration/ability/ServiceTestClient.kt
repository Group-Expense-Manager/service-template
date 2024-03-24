package pl.edu.agh.gem.integration.ability

import org.springframework.context.annotation.Lazy
import org.springframework.http.HttpHeaders.ACCEPT
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.stereotype.Component
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec
import org.springframework.test.web.servlet.client.MockMvcWebTestClient
import org.springframework.web.context.WebApplicationContext
import pl.edu.agh.gem.external.dto.product.ProductRequest
import pl.edu.agh.gem.external.utils.InternalApiMediaType
import pl.edu.agh.gem.external.utils.InternalApiMediaType.APPLICATION_JSON_INTERNAL_VER_1
import java.net.URI
@Component
@Lazy
class ServiceTestClient(applicationContext: WebApplicationContext) {
    private val webClient = MockMvcWebTestClient.bindToApplicationContext(applicationContext)
        .configureClient()
        .build()

    fun findProduct(id: String): ResponseSpec {
        return webClient.post()
            .uri(URI("/api/products/$id"))
            .header(ACCEPT, APPLICATION_JSON_INTERNAL_VER_1)
            .exchange()
    }

    fun createProduct(body:ProductRequest):ResponseSpec{
        return webClient.post()
                .uri(URI("/api/products"))
                .header(CONTENT_TYPE, APPLICATION_JSON_INTERNAL_VER_1)
                .header(ACCEPT, APPLICATION_JSON_INTERNAL_VER_1)
                .bodyValue(body)
                .exchange()
    }
}
