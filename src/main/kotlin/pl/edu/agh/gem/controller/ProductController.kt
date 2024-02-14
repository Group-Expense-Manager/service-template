package pl.edu.agh.gem.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.edu.agh.gem.service.ProductService

@RestController()
@RequestMapping("api/v1")
class ProductController(
    val productService: ProductService,
) {
    @GetMapping("name")
    fun getAll(): List<String> {
        return productService.getAllNames().collectList().block() as ArrayList<String>
    }
}
