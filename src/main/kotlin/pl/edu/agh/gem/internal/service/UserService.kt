package pl.edu.agh.gem.internal.service

import org.springframework.stereotype.Service
import pl.edu.agh.gem.internal.client.ExampleClient
import pl.edu.agh.gem.internal.domain.UserAttachment
import pl.edu.agh.gem.internal.persistence.UserAttachmentRepository

@Service
class UserService(
    private val userAttachmentRepository: UserAttachmentRepository,
    private val exampleClient: ExampleClient,
) {
    fun find(id: String): UserAttachment {
        return userAttachmentRepository.find(id) ?: throw MissingProductException(id)
    }

    fun save(userAttachment: UserAttachment) {
        return userAttachmentRepository.save(userAttachment)
    }

    fun send(userAttachment: UserAttachment) {
        exampleClient.postProduct(userAttachment)
    }
}

class MissingProductException(id: String) : RuntimeException("Failed to find product with id:$id")
