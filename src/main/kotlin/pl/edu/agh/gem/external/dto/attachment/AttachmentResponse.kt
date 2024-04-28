package pl.edu.agh.gem.external.dto.attachment

import pl.edu.agh.gem.internal.domain.UserAttachment

data class AttachmentResponse(
    val id: String,
    val name: String,
) {
    companion object {
        fun from(userAttachment: UserAttachment): AttachmentResponse {
            return AttachmentResponse(
                id = userAttachment.id,
                name = userAttachment.name,
            )
        }
    }
}
