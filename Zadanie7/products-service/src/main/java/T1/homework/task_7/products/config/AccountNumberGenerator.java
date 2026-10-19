package T1.homework.task_7.products.config;

import T1.homework.task_7.products.model.ProductEntity;
import jakarta.persistence.PrePersist;
import java.util.concurrent.atomic.AtomicLong;

public class AccountNumberGenerator {

    private static final AtomicLong counter = new AtomicLong(1000);

    @PrePersist
    public void generateAccountNumber(Object entity) {
        if (entity instanceof ProductEntity product) {
            if (product.getAccountNumber() == null || product.getAccountNumber().isEmpty()) {
                product.setAccountNumber("ACC" + counter.getAndIncrement());
            }
        }
    }
}