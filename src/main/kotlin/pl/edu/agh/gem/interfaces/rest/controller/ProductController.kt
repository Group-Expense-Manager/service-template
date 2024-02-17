package pl.edu.agh.gem.interfaces.rest.controller


import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import pl.edu.agh.gem.application.ProductService
import pl.edu.agh.gem.interfaces.rest.model.ProductResponse

@RestController
@RequestMapping("api/v1")
class ProductController(
    val productService: ProductService,
) {
    @GetMapping("names")
    @ResponseStatus(HttpStatus.OK)
    fun getAll(): List<ProductResponse> {
        return productService.getAll().collectList().block() as ArrayList<ProductResponse>
    }
}
