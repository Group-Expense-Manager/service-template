package pl.edu.agh.gem

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class ServiceTemplateApplication

fun main(args: Array<String>) {
    runApplication<ServiceTemplateApplication>(*args)
}
