package pl.edu.agh.gem.infrastructure.persistence

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import pl.edu.agh.gem.domain.Product

@Repository
interface ProductRepository : ReactiveMongoRepository<Product, String>
