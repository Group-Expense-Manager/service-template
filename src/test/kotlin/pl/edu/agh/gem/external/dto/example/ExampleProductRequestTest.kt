package pl.edu.agh.gem.external.dto.example

class ExampleProductRequestTest : BehaviorSpec({
    val productRepository = mock<ProductRepository>()
    val productService = ProductService(
        productRepository
    )

    should("sth") {
        // given
        whenever(productService.getAll()).thenReturn(listOf())

        // when
        val result = productService.getAll()

        // then
        result shouldHaveSize 5
    }
})
