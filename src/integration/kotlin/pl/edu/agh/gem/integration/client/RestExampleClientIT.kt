package pl.edu.agh.gem.integration.client

import io.kotest.matchers.shouldBe
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpHeaders.ACCEPT
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.HttpMethod.GET
import org.springframework.http.HttpMethod.POST
import org.springframework.http.HttpStatus.OK
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException
import org.springframework.web.client.RestTemplate
import pl.edu.agh.gem.config.ExampleProperties
import pl.edu.agh.gem.external.dto.example.ExampleProductRequest
import pl.edu.agh.gem.external.dto.example.ExampleProductResponse
import pl.edu.agh.gem.external.dto.product.ProductResponse
import pl.edu.agh.gem.external.utils.InternalApiMediaType.APPLICATION_JSON_INTERNAL_VER_1
import pl.edu.agh.gem.integration.BaseIntegrationSpec
import pl.edu.agh.gem.integration.ability.stubExamplePostProduct
import pl.edu.agh.gem.integration.assertion.shouldHaveBody
import pl.edu.agh.gem.integration.assertion.shouldHaveHttpStatus
import pl.edu.agh.gem.internal.client.ExampleClient
import pl.edu.agh.gem.internal.client.ExampleClientException
import pl.edu.agh.gem.internal.client.RetryableExampleClientException
import pl.edu.agh.gem.internal.domain.Product
import pl.edu.agh.gem.util.createExampleProductRespone
import pl.edu.agh.gem.util.createProduct
import pl.edu.agh.gem.util.createProductRequest

@Component
class RestExampleClientIT(
        private val exampleClient: ExampleClient
) : BaseIntegrationSpec({
    should("get product"){
        //given
        val exampleProductResponse = createExampleProductRespone()
        stubExamplePostProduct(exampleProductResponse,OK)

        //when
        val result = exampleClient.getProduct(exampleProductResponse.id)

        //then
        result.also {
            it.id shouldBe exampleProductResponse.id
            it.name shouldBe exampleProductResponse.name
        }
    }

})
