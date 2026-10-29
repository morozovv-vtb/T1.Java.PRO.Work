package T1.homework.task_7.payment.controller;

import T1.homework.task_7.common.dto.ProductDto;
import T1.homework.task_7.payment.dto.PaymentRequest;
import T1.homework.task_7.payment.dto.PaymentResponse;
import T1.homework.task_7.payment.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // GET /products/user/{userId}
    @GetMapping("/products/user/{userId}")
    public ResponseEntity<List<ProductDto>> getProductsByUserId(@PathVariable Long userId) {
        List<ProductDto> products = paymentService.getProductsByUserId(userId);
        return ResponseEntity.ok(products);
    }

    // GET /products/{productId}
    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long productId) {
        ProductDto product = paymentService.getProductById(productId);
        return ResponseEntity.ok(product);
    }

    // POST /execute
    @PostMapping("/execute")
    public ResponseEntity<PaymentResponse> executePayment(@RequestBody PaymentRequest request) {
        Long productId = request.getProductId();
        BigDecimal amount = request.getAmount();

        paymentService.executePayment(productId, amount);

        PaymentResponse response = new PaymentResponse("Payment successful", true);
        return ResponseEntity.ok(response);
    }
}