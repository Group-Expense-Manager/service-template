package pl.edu.agh.gem.external.client

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class OtherServiceClient {
    @Autowired
    lateinit var restTemplate: RestTemplate

    @Value("\${client.url}")
    lateinit var url: String

    fun getSomeData(): String {
        val response = restTemplate.getForObject(url, String::class.java)

        return response ?: "No data"
    }
}
