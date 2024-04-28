package pl.edu.agh.gem.external.persistence

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Repository
import pl.edu.agh.gem.internal.domain.UserAttachment
import pl.edu.agh.gem.internal.persistence.UserAttachmentRepository

@Repository
class MongoUserAttachmentRepository(private val mongo: MongoTemplate) : UserAttachmentRepository {

    override fun save(userAttachment: UserAttachment) {
        mongo.save(userAttachment.toEntity())
    }
}

private fun UserAttachment.toEntity() = UserAttachmentEntity(
    id = id,
    name = name,
)
