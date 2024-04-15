package pl.edu.agh.gem.external.client

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpHeaders.ACCEPT
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.HttpMethod.GET
import org.springframework.http.HttpMethod.POST
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException
import org.springframework.web.client.RestTemplate
import pl.edu.agh.gem.config.ExampleProperties
import pl.edu.agh.gem.external.dto.example.ExampleProductRequest
import pl.edu.agh.gem.external.dto.example.ExampleProductResponse
import pl.edu.agh.gem.internal.client.ExampleClient
import pl.edu.agh.gem.internal.client.ExampleClientException
import pl.edu.agh.gem.internal.client.RetryableExampleClientException
import pl.edu.agh.gem.internal.domain.Product
import pl.edu.agh.gem.media.InternalApiMediaType.APPLICATION_JSON_INTERNAL_VER_1

@Component
class RestExampleClient(
    @Qualifier("ExampleRestTemplate") val restTemplate: RestTemplate,
    val exampleProperties: ExampleProperties,
) : ExampleClient {

    override fun postProduct(product: Product) {
        try {
            restTemplate.exchange(
                resolveProductsAddress(),
                POST,
                HttpEntity(ExampleProductRequest.from(product), createHeaders()),
                Any::class.java,
            )
        } catch (ex: HttpClientErrorException) {
            logger.warn(ex) { "Client side exception while trying to share product: $product" }
            throw ExampleClientException(ex.message)
        } catch (ex: HttpServerErrorException) {
            logger.warn(ex) { "Server side exception while trying to share product: $product" }
            throw RetryableExampleClientException(ex.message)
        } catch (ex: Exception) {
            logger.warn(ex) { "Unexpected exception while trying to share product: $product" }
            throw ExampleClientException(ex.message)
        }
    }
    override fun getProduct(productId: String): Product {
        return try {
            restTemplate.exchange(
                resolveProductsAddress(productId),
                GET,
                HttpEntity(null, createHeaders()),
                ExampleProductResponse::class.java,
            ).body?.toDomain() ?: throw ExampleClientException(
                "While retrieving product using ExampleClient we receive empty body",
            )
        } catch (ex: HttpClientErrorException) {
            logger.warn(ex) { "Client side exception while trying to share product with id: $productId" }
            throw ExampleClientException(ex.message)
        } catch (ex: HttpServerErrorException) {
            logger.warn(ex) { "Server side exception while trying to share product with id: $productId" }
            throw ExampleClientException(ex.message)
        } catch (ex: Exception) {
            logger.warn(ex) { "Unexpected exception while trying to share product with id: $productId" }
            throw ExampleClientException(ex.message)
        }
    }

    private fun resolveProductsAddress() =
        "${exampleProperties.url}/api/example"
    private fun resolveProductsAddress(productId: String) =
        "${exampleProperties.url}/api/example/$productId"

    private fun createHeaders() =
        HttpHeaders().apply {
            set(ACCEPT, APPLICATION_JSON_INTERNAL_VER_1)
            set(CONTENT_TYPE, APPLICATION_JSON_INTERNAL_VER_1)
        }
    companion object {
        private val logger = KotlinLogging.logger {}
    }
}
