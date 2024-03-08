package pl.edu.agh.gem.external.persistence

import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.stereotype.Repository
import pl.edu.agh.gem.internal.domain.Product
import pl.edu.agh.gem.internal.persistence.ProductRepository
import reactor.core.publisher.Flux

@Repository
class MongoProductRepository(private val mongo: ReactiveMongoTemplate) : ProductRepository {
    override fun findAll(): Flux<Product> {
        return mongo.findAll(ProductEntity::class.java).map { it.toDomain() }
    }
}
