package pl.edu.agh.gem.external.dto.attachment

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import pl.edu.agh.gem.util.createProduct

class ProductResponseTest : ShouldSpec({

    should("map correct to Product") {
        // given
        val product = createProduct(
            id = "id",
            name = "name",
        )

        // when
        val productResponse = AttachmentResponse.from(product)

        // then
        productResponse.also {
            it.id shouldBe "id"
            it.name shouldBe "name"
        }
    }
},)
