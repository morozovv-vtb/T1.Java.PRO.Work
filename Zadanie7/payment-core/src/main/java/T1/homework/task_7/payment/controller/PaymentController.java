package T1.homework.task_7.payment.controller;

import T1.homework.task_7.common.dto.ProductDto;
import T1.homework.task_7.payment.dto.PaymentRequest;
import T1.homework.task_7.payment.dto.PaymentResponse;
import T1.homework.task_7.payment.service.PaymentService;
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
    public List<ProductDto> getProductsByUserId(@PathVariable Long userId) {
        return paymentService.getProductsByUserId(userId);
    }

    // GET /products/{productId}
    @GetMapping("/products/{productId}")
    public ProductDto getProductById(@PathVariable Long productId) {
        return paymentService.getProductById(productId);
    }

    // POST /execute
    @PostMapping("/execute")
    public PaymentResponse executePayment(@RequestBody PaymentRequest request) {
        Long productId = request.getProductId();
        BigDecimal amount = request.getAmount();

        paymentService.executePayment(productId, amount);

        return new PaymentResponse("Payment successful", true);
    }
}