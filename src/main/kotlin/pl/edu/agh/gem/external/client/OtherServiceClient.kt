package pl.edu.agh.gem.external.client

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class OtherServiceClient {
    @Autowired
    lateinit var restTemplate: RestTemplate

    fun getSomeData(): String {
        val url = "url"

        val response = restTemplate.getForObject(url, String::class.java)

        return response ?: "No data"
    }
}
