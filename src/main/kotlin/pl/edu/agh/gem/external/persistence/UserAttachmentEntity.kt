package pl.edu.agh.gem.external.persistence

import org.bson.types.Binary
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document("userAttachments")
data class UserAttachmentEntity(
    @Id
    val id: String,
    val userId: String,
    val contentType:String,
    val sizeInBytes:Long,
    val data: Binary,
    val createdAt: Instant,
    val updatedAt: Instant,
    val attachmentHistory: List<AttachmentHistoryEntity>,
)
