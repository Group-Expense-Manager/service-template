package pl.edu.agh.gem

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ServiceTemplateApplication

fun main(args: Array<String>) {
    runApplication<ServiceTemplateApplication>(*args)
}
