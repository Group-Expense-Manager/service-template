package pl.edu.agh.gem.util

import pl.edu.agh.gem.external.dto.example.ExampleProductResponse
import pl.edu.agh.gem.internal.domain.UserAttachment

fun createProduct(
    id: String = "id",
    name: String = "name",
) = UserAttachment(
    id = id,
    name = name,
)

fun createProductRequest(
    name: String = "name",
) = ProductRequest(
    name = name,
)

fun createExampleProductResponse(
    id: String = "id",
    name: String = "name",
) = ExampleProductResponse(
    id = id,
    name = name,
)
