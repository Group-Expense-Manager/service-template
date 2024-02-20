package pl.edu.agh.gem.internal.service

import org.springframework.stereotype.Service
import pl.edu.agh.gem.external.dto.ProductResponse
import pl.edu.agh.gem.internal.domain.Product
import pl.edu.agh.gem.internal.persistence.ProductRepository
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
