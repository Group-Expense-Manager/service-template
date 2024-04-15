package pl.edu.agh.gem.external.dto.product

import pl.edu.agh.gem.internal.domain.Product

data class ProductResponse(
    val id: String,
    val name: String,
) {
    companion object {
        fun from(product: Product): ProductResponse {
            return ProductResponse(
                id = product.id,
                name = product.name,
            )
        }
    }
}
