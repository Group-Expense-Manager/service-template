package pl.edu.agh.gem.internal.persistence

import pl.edu.agh.gem.internal.domain.UserAttachment

interface UserAttachmentRepository {
    fun save(userAttachment: UserAttachment)
}
