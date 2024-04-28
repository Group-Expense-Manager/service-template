package pl.edu.agh.gem.external.controller

import org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.edu.agh.gem.external.dto.attachment.AttachmentResponse
import pl.edu.agh.gem.internal.service.UserService
import pl.edu.agh.gem.media.InternalApiMediaType.APPLICATION_JSON_INTERNAL_VER_1

@RestController
@RequestMapping("/api/products")
class UserController(
    val userService: UserService,
) {

    @PostMapping(consumes = [APPLICATION_OCTET_STREAM_VALUE], produces = [APPLICATION_JSON_INTERNAL_VER_1])
    fun createProduct(
        @RequestBody fileBytes: ByteArray,
    ): AttachmentResponse {
        return AttachmentResponse.from(userService.save(productRequest.toDomain()))
    }
}
