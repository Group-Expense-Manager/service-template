package pl.edu.agh.gem.repository

import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import pl.edu.agh.gem.model.Product
import reactor.core.publisher.Mono

@Repository
interface ProductRepository : ReactiveMongoRepository<Product, String> {
    fun findProductById(id: ObjectId): Mono<Product>
}
