package pl.edu.agh.gem.integration.client

import io.kotest.matchers.shouldBe
import org.springframework.http.HttpStatus.OK
import org.springframework.stereotype.Component
import pl.edu.agh.gem.integration.BaseIntegrationSpec
import pl.edu.agh.gem.integration.ability.stubExamplePostProduct
import pl.edu.agh.gem.internal.client.ExampleClient
import pl.edu.agh.gem.util.createExampleProductRespone

@Component
class RestExampleClientIT(
    private val exampleClient: ExampleClient
) : BaseIntegrationSpec({
    should("get product") {
        // given
        val exampleProductResponse = createExampleProductRespone()
        stubExamplePostProduct(exampleProductResponse, OK)

        // when
        val result = exampleClient.getProduct(exampleProductResponse.id)

        // then
        result.also {
            it.id shouldBe exampleProductResponse.id
            it.name shouldBe exampleProductResponse.name
        }
    }
})
