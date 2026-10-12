package T1.homework.task_6.service;

import T1.homework.task_6.model.ProductEntity;
import T1.homework.task_6.model.UserEntity;
import T1.homework.task_6.repository.ProductRepository;
import T1.homework.task_6.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ProductService(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ProductEntity createProduct(String accountNumber, BigDecimal balance, String productType, Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " not found"));

        ProductEntity product = new ProductEntity(accountNumber, balance, productType, user);
        return productRepository.save(product);
    }

    public List<ProductEntity> getAllProductsByUserId(Long userId) {
        return productRepository.findByUserId(userId);
    }

    public Optional<ProductEntity> getProductById(Long productId) {
        return productRepository.findByIdWithUser(productId);
    }

    public List<ProductEntity> getAllProducts() {
        return productRepository.findAllWithUser();
    }

    @Transactional
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}