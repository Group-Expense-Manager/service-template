package pl.edu.agh.gem.external.persistence

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import pl.edu.agh.gem.internal.domain.Product

@Document("products")
data class ProductEntity(
    @Id
    val id: ObjectId = ObjectId(),
    val name: String,
) {
    fun toDomain() =
        Product(
            name = name,
        )
}
