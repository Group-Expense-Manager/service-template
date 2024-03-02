package pl.edu.agh.gem.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import java.time.Duration

@Configuration
class RestTemplateConfig {
    @Value("\${client.connectTimeout}")
    var connectTimeout: Long? = null

    @Value("\${client.readTimeout}")
    var readTimeout: Long? = null

    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplateBuilder()
            .setConnectTimeout(Duration.ofMillis(connectTimeout!!))
            .setReadTimeout(Duration.ofMillis(readTimeout!!))
            .build()
    }
}
