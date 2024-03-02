package pl.edu.agh.gem.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.web.client.RestTemplate
import java.time.Duration

@Configuration
@PropertySource("classpath:client.properties")
class ClientConfig {
    @Bean
    @Qualifier("OtherServiceRestTemplate")
    fun exampleRestTemplate(
        @Qualifier("SomeClient")settings: Settings,
    ): RestTemplate {
        return RestTemplateBuilder()
            .setConnectTimeout(settings.connectTimeout)
            .setReadTimeout(settings.readTimeout)
            .build()
    }

    @Bean
    @Qualifier("SomeClient")
    @ConfigurationProperties(prefix = "some-client")
    fun getSettings(): Settings {
        return Settings()
    }
}

data class Settings(
    var url: String = "",
    var connectTimeout: Duration = Duration.ofMillis(0),
    var readTimeout: Duration = Duration.ofMillis(0),
)
