package pl.edu.agh.gem.application

import org.springframework.stereotype.Service
import pl.edu.agh.gem.domain.Product
import pl.edu.agh.gem.infrastructure.persistence.ProductRepository
import pl.edu.agh.gem.interfaces.rest.model.ProductResponse
import reactor.core.publisher.Flux

@Service
class ProductService(
    val productRepository: ProductRepository,
) {
    fun getAll(): Flux<ProductResponse> {
        return productRepository.findAll().map { product -> mapToProductResponse(product) }
    }

    private fun mapToProductResponse(product: Product): ProductResponse {
        return ProductResponse(product.name)
    }
}
