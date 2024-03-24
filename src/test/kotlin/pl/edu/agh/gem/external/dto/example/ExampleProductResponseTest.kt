package pl.edu.agh.gem.external.dto.example

import pl.edu.agh.gem.internal.domain.Product

data class ExampleProductResponseTest(
    val name: String
) {
    fun toDomain() =
        Product(
            name = name
        )
}
