package pl.edu.agh.gem.external.client

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import pl.edu.agh.gem.config.Settings

@Component
class OtherServiceClient(
    @Qualifier("OtherServiceRestTemplate") val restTemplate: RestTemplate,
    @Qualifier("SomeClient") val settings: Settings,
) {
    fun getSomeData(): String {
        val response = restTemplate.getForObject(settings.url, String::class.java)

        return response ?: "No data"
    }
}
