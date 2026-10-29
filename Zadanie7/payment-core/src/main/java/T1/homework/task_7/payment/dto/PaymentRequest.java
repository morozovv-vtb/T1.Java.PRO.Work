package T1.homework.task_7.payment.dto;

import java.math.BigDecimal;

public class PaymentRequest {
    private Long productId;
    private BigDecimal amount;

    // Конструктор по умолчанию — обязательно для Jackson
    public PaymentRequest() {}

    public PaymentRequest(Long productId, BigDecimal amount) {
        this.productId = productId;
        this.amount = amount;
    }

    // Getters and Setters
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
}