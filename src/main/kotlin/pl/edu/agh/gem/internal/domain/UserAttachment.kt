package pl.edu.agh.gem.internal.domain

import org.bson.types.Binary
import pl.edu.agh.gem.external.persistence.AttachmentHistoryEntity
import java.time.Instant

data class UserAttachment(
    val id: String,
    val userId: String,
    val contentType:String,
    val sizeInBytes:Long,
    val data: Binary,
    val createdAt: Instant,
    val updatedAt: Instant,
    val attachmentHistory: List<AttachmentHistoryEntity>,
)
