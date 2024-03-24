package pl.edu.agh.gem.internal.service

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.should
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import pl.edu.agh.gem.internal.persistence.ProductRepository

class ProductServiceTest : BehaviorSpec({
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
