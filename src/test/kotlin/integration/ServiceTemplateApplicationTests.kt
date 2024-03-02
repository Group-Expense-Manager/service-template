package integration

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@AutoConfigureMockMvc
@SpringBootTest(classes = [pl.edu.agh.gem.ServiceTemplateApplication::class])
class ServiceTemplateApplicationTests(
    @Autowired val mockMvc: MockMvc,
) : MongoDBContainerSetup {
    @Test
    fun shouldReturnAllProduct() {
        mockMvc.perform(
            MockMvcRequestBuilders
                .get("/api/v1/names")
                .contentType(MediaType.APPLICATION_JSON),
        ).andExpectAll(status().isOk)
    }
}
