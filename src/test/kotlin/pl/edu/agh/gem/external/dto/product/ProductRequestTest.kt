package pl.edu.agh.gem.external.dto.product

import jakarta.validation.constraints.NotBlank
import pl.edu.agh.gem.internal.domain.Product

data class ProductRequestTest(
    @field:NotBlank val name: String
) {
    fun toDomain() = Product(
        name = name
    )
}
