package pl.edu.agh.gem.config

import com.fasterxml.jackson.module.afterburner.AfterburnerModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature.NullIsSameAsDefault
import com.fasterxml.jackson.module.kotlin.KotlinFeature.NullToEmptyCollection
import com.fasterxml.jackson.module.kotlin.KotlinFeature.NullToEmptyMap
import com.fasterxml.jackson.module.kotlin.KotlinFeature.SingletonSupport
import com.fasterxml.jackson.module.kotlin.KotlinFeature.StrictNullChecks
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.i18n.SessionLocaleResolver
import java.util.Locale.ENGLISH

@Configuration
class ApplicationConfig {

    @Bean
    fun kotlinModule() = KotlinModule.Builder()
        .withReflectionCacheSize(512)
        .configure(NullToEmptyCollection, false)
        .configure(NullToEmptyMap, false)
        .configure(NullIsSameAsDefault, false)
        .configure(SingletonSupport, false)
        .configure(StrictNullChecks, false)
        .build()

    @Bean
    fun afterburnerModule() = AfterburnerModule()

    @Bean
    fun localeResolver(): LocaleResolver {
        return SessionLocaleResolver().apply {
            this.setDefaultLocale(ENGLISH)
        }
    }
}