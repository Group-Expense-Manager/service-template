package pl.edu.agh.gem.domain

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("products")
data class Product(
    @Id
    val id: ObjectId = ObjectId(),
    val name: String,
)
