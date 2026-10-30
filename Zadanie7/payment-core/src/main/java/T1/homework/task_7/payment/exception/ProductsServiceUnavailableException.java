package T1.homework.task_7.payment.exception;

public class ProductsServiceUnavailableException extends PaymentCoreException {
    public ProductsServiceUnavailableException() {
        super("Products service is unavailable or returned error");
    }
}