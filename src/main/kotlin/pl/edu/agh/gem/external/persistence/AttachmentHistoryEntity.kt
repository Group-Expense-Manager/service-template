package pl.edu.agh.gem.external.persistence

import java.time.Instant

data class AttachmentHistoryEntity (
    val updatedBy:String,
    val updatedAt: Instant,
    val sizeInBytes: Long,
)
