package T1.homework.task_6.controller;

import T1.homework.task_6.dto.ProductDto;
import T1.homework.task_6.exception.ProductNotFoundException;
import T1.homework.task_6.mapper.ProductMapper;
import T1.homework.task_6.service.ProductService;
import T1.homework.task_6.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;
    private final UserService userService;
    private final ProductMapper productMapper; // ✅ Маппер

    public ProductController(ProductService productService, UserService userService, ProductMapper productMapper) {
        this.productService = productService;
        this.userService = userService;
        this.productMapper = productMapper;
    }

    // POST /api/v1/products — создать продукт
    @PostMapping
    public ProductDto createProduct(
            @RequestParam String accountNumber,
            @RequestParam BigDecimal balance,
            @RequestParam String productType,
            @RequestParam Long userId) {

        userService.validateUserExists(userId);
        return productService.createProduct(accountNumber, balance, productType, userId);
    }

    // GET /api/v1/products/user/{userId} — получить все продукты пользователя
    @GetMapping("/user/{userId}")
    public List<ProductDto> getProductsByUserId(@PathVariable Long userId) {
        userService.validateUserExists(userId);
        return productService.getAllProductsByUserId(userId);
    }

    // GET /api/v1/products/{id} — получить продукт по ID
    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    // GET /api/v1/products — получить все продукты
    @GetMapping
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    // DELETE /api/v1/products/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        if (!productService.getProductById(id).isPresent()) {
            throw new ProductNotFoundException(id);
        }
        productService.deleteProduct(id);
    }
}