package pl.edu.agh.gem.integration.controler

import org.springframework.http.HttpStatus.OK
import pl.edu.agh.gem.external.dto.product.ProductResponse
import pl.edu.agh.gem.integration.BaseIntegrationSpec
import pl.edu.agh.gem.integration.ability.ServiceTestClient
import pl.edu.agh.gem.integration.assertion.shouldHaveBody
import pl.edu.agh.gem.integration.assertion.shouldHaveHttpStatus
import pl.edu.agh.gem.internal.persistence.ProductRepository
import pl.edu.agh.gem.util.createProduct
import pl.edu.agh.gem.util.createProductRequest

class ProductControllerIT(
    private val service: ServiceTestClient,
    private val productRepository: ProductRepository
) : BaseIntegrationSpec({
    should("find product") {
        // given
        val product = createProduct()
        productRepository.save(product)

        // when
        val response = service.findProduct(product.id)

        // then
        response shouldHaveHttpStatus OK
        response shouldHaveBody ProductResponse(
            id = product.id,
            name = product.name
        )
    }

    should("create product") {
        // given
        val productRequest = createProductRequest()

        // when
        val response = service.createProduct(productRequest)

        // then
        response shouldHaveHttpStatus OK
    }
})
