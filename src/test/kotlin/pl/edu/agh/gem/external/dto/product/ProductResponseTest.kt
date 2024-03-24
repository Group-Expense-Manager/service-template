package pl.edu.agh.gem.external.dto.product

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.should
import org.mockito.kotlin.whenever

class ProductResponseTest : BehaviorSpec({

    should("map correct to Product") {
        // given
        whenever(productService.getAll()).thenReturn(listOf())

        // when
        val result = productService.getAll()

        // then
        result shouldHaveSize 5
    }
})
