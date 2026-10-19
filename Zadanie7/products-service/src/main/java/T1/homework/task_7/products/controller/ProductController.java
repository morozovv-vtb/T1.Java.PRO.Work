package T1.homework.task_7.products.controller;

import T1.homework.task_7.common.dto.ProductDto;
import T1.homework.task_7.products.mapper.ProductMapper;
import T1.homework.task_7.products.service.ProductService;
import T1.homework.task_7.products.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;
    private final UserService userService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService,
                             UserService userService,
                             ProductMapper productMapper
                            ) {
        this.productService = productService;
        this.userService = userService;
        this.productMapper = productMapper;
    }

    // POST /api/v1/products
    @PostMapping
    public ProductDto createProduct(@RequestBody ProductDto productDto) {
        // Проверяем, что пользователь существует
        userService.validateUserExists(productDto.getUserId());
        // Передаём DTO в сервис — он сам преобразует в Entity
        return productService.createProduct(productDto);
    }

    // GET /api/v1/products/user/{userId}
    @GetMapping("/user/{userId}")
    public List<ProductDto> getProductsByUserId(@PathVariable Long userId) {
        userService.validateUserExists(userId);
        return productService.getAllProductsByUserId(userId);
    }

    // GET /api/v1/products/{id}
    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    // GET /api/v1/products
    @GetMapping
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    // DELETE /api/v1/products/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}