package pl.edu.agh.gem.application

import org.springframework.stereotype.Service
import pl.edu.agh.gem.infrastructure.persistence.ProductRepository
import reactor.core.publisher.Flux

@Service
class ProductService(
    val productRepository: ProductRepository,
) {
    fun getAllNames(): Flux<String> {
        return productRepository.findAll().map { product -> product.name }
    }
}
