package pl.edu.agh.gem.internal.persistence

import pl.edu.agh.gem.internal.domain.Product
import reactor.core.publisher.Flux

interface ProductRepository {
    fun findAll(): Flux<Product>
}
