/*
package T1.homework.task_6.controller;

import T1.homework.task_6.exception.ProductNotFoundException;
import T1.homework.task_6.exception.UserNotFoundException;
import T1.homework.task_6.model.ProductEntity;
import T1.homework.task_6.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // POST /api/v1/products — создать продукт
    @PostMapping
    public ProductEntity createProduct(
            @RequestParam String accountNumber,
            @RequestParam BigDecimal balance,
            @RequestParam String productType,
            @RequestParam Long userId) {
        return productService.createProduct(accountNumber, balance, productType, userId);
    }

    // GET /api/v1/products/user/{userId} — получить все продукты пользователя
    @GetMapping("/user/{userId}")
    public List<ProductEntity> getProductsByUserId(@PathVariable Long userId) {
        List<ProductEntity> products = productService.getAllProductsByUserId(userId);
        if (products.isEmpty()) {
            throw new UserNotFoundException(userId); // или UserNotFoundException — в зависимости от логики
        }
        return products;
    }

    // GET /api/v1/products/{id} — получить продукт по ID
    @GetMapping("/{id}")
    public ProductEntity getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    // GET /api/v1/products — получить все продукты
    @GetMapping
    public List<ProductEntity> getAllProducts() {
        return productService.getAllProducts();
    }

    // DELETE /api/v1/products/{id}
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        if (!productService.getProductById(id).isPresent()) {
            throw new ProductNotFoundException(id);
        }
        productService.deleteProduct(id);
    }
}*/

package T1.homework.task_6.controller;

import T1.homework.task_6.exception.ProductNotFoundException;
import T1.homework.task_6.exception.UserNotFoundException;
import T1.homework.task_6.model.ProductEntity;
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

    public ProductController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    // Централизованная проверка пользователя
    private void validateUserExists(Long userId) {
        if (userService.getUserById(userId) == null) {
            throw new UserNotFoundException(userId);
        }
    }

    // POST /api/v1/products — создать продукт
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductEntity createProduct(
            @RequestParam String accountNumber,
            @RequestParam BigDecimal balance,
            @RequestParam String productType,
            @RequestParam Long userId) {

        validateUserExists(userId);
        return productService.createProduct(accountNumber, balance, productType, userId);
    }

    // GET /api/v1/products/user/{userId} — получить все продукты пользователя
    @GetMapping("/user/{userId}")
    public List<ProductEntity> getProductsByUserId(@PathVariable Long userId) {
        validateUserExists(userId);
        return productService.getAllProductsByUserId(userId);
    }

    // GET /api/v1/products/{id} — получить продукт по ID
    @GetMapping("/{id}")
    public ProductEntity getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    // GET /api/v1/products — получить все продукты
    @GetMapping
    public List<ProductEntity> getAllProducts() {
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