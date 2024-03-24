package pl.edu.agh.gem.external.controller

import jakarta.validation.Valid
import org.springframework.http.HttpStatus.OK
import org.springframework.web.bind.annotation.*
import pl.edu.agh.gem.external.dto.product.ProductRequest
import pl.edu.agh.gem.external.dto.product.ProductResponse
import pl.edu.agh.gem.internal.service.ProductService

@RestController
@RequestMapping("/api/products")
class ProductController(
    val productService: ProductService
) {
    @GetMapping("/{id}")
    @ResponseStatus(OK)
    fun findOne(
            @PathVariable id: String
    ): ProductResponse {
        val product = productService.find(id)
        return ProductResponse.from(product)
    }

    @PostMapping
    @ResponseStatus(OK)
    fun createProduct(
        @Valid @RequestBody
        productRequest: ProductRequest
    ) {
        productService.save(productRequest.toDomain())
    }
}
