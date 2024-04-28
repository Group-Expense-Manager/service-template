package pl.edu.agh.gem.internal.client

import pl.edu.agh.gem.internal.domain.UserAttachment

interface ExampleClient {
    fun postProduct(userAttachment: UserAttachment)

    fun getProduct(productId: String): UserAttachment
}

class ExampleClientException(override val message: String?) : RuntimeException()

class RetryableExampleClientException(override val message: String?) : RuntimeException()
