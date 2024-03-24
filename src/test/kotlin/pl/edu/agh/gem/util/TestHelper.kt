package pl.edu.agh.gem.util

import pl.edu.agh.gem.internal.domain.Product

fun createProduct(
    id: String = "id",
    name: String = "name"
) = Product(
    id = id,
    name = name
)
