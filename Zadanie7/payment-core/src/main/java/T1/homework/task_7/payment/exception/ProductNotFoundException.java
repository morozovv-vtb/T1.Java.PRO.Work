package T1.homework.task_7.payment.exception;

public class ProductNotFoundException extends PaymentCoreException {
    public ProductNotFoundException(Long productId) {
        super("Product with id " + productId + " not found");
    }
}