package pl.edu.agh.gem.internal.service

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import pl.edu.agh.gem.internal.client.ExampleClient
import pl.edu.agh.gem.internal.persistence.UserAttachmentRepository
import pl.edu.agh.gem.util.createProduct

class ProductServiceTest : ShouldSpec({
    val userAttachmentRepository = mock<UserAttachmentRepository>()
    val exampleClient = mock<ExampleClient> { }
    val userService = UserService(
        userAttachmentRepository = userAttachmentRepository,
        exampleClient = exampleClient,
    )

    should("find product") {
        // given
        val product = createProduct()
        whenever(userAttachmentRepository.find(product.id)).thenReturn(product)

        // when
        val result = userService.find(product.id)

        // then
        result shouldBe product
    }

    should("save product") {
        // given
        val product = createProduct()

        // when
        userService.save(product)

        // then
        verify(userAttachmentRepository, times(1)).save(product)
    }

    should("send product") {
        // given
        val product = createProduct()

        // when
        userService.send(product)

        // then
        verify(exampleClient, times(1)).postProduct(product)
    }
},)
