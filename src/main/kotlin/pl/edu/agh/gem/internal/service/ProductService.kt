package pl.edu.agh.gem.internal.service

import org.springframework.stereotype.Service
import pl.edu.agh.gem.internal.domain.Product
import pl.edu.agh.gem.internal.persistence.ProductRepository
import reactor.core.publisher.Flux

@Service
class ProductService(
    val productRepository: ProductRepository,
) {
    fun getAll(): Flux<Product> {
        return productRepository.findAll()
    }
}
