/*
package T1.homework.task_6.controller;

import T1.homework.task_6.model.ProductEntity;
import T1.homework.task_6.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // POST /api/products — создать продукт
    @PostMapping
    public ResponseEntity<ProductEntity> createProduct(
            @RequestParam String accountNumber,
            @RequestParam BigDecimal balance,
            @RequestParam String productType,
            @RequestParam Long userId) {
        ProductEntity product = productService.createProduct(accountNumber, balance, productType, userId);
        return ResponseEntity.ok(product);
    }

    // GET /api/products/user/{userId} — получить все продукты пользователя
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProductEntity>> getProductsByUserId(@PathVariable Long userId) {
        List<ProductEntity> products = productService.getAllProductsByUserId(userId);
        return ResponseEntity.ok(products);
    }

    // GET /api/products/{id} — получить продукт по ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductEntity> getProductById(@PathVariable Long id) {
        Optional<ProductEntity> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/products — получить все продукты
    @GetMapping
    public ResponseEntity<List<ProductEntity>> getAllProducts() {
        List<ProductEntity> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    // DELETE /api/products/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}*/

package T1.homework.task_6.controller;

import T1.homework.task_6.model.ProductEntity;
import T1.homework.task_6.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // POST /api/products — создать продукт
    @PostMapping
    public ResponseEntity<ProductEntity> createProduct(
            @RequestParam String accountNumber,
            @RequestParam BigDecimal balance,
            @RequestParam String productType,
            @RequestParam Long userId) {
        ProductEntity product = productService.createProduct(accountNumber, balance, productType, userId);
        return ResponseEntity.ok(product);
    }

    // GET /api/products/user/{userId} — получить все продукты пользователя
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProductEntity>> getProductsByUserId(@PathVariable Long userId) {
        List<ProductEntity> products = productService.getAllProductsByUserId(userId);
        return ResponseEntity.ok(products);
    }

    // GET /api/products/{id} — получить продукт по ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductEntity> getProductById(@PathVariable Long id) {
        Optional<ProductEntity> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/products — получить все продукты
    @GetMapping
    public ResponseEntity<List<ProductEntity>> getAllProducts() {
        List<ProductEntity> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    // DELETE /api/products/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
