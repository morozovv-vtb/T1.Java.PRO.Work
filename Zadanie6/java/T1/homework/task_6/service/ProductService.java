package T1.homework.task_6.service;

import T1.homework.task_6.dto.ProductDto;
import T1.homework.task_6.exception.UserNotFoundException;
import T1.homework.task_6.model.ProductEntity;
import T1.homework.task_6.model.UserEntity;
import T1.homework.task_6.repository.ProductRepository;
import T1.homework.task_6.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;

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
    public ProductDto createProduct(String accountNumber, BigDecimal balance, String productType, Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        ProductEntity product = new ProductEntity(accountNumber, balance, productType, user);
        ProductEntity saved = productRepository.save(product);

        // Возвращаем DTO — не Entity!
        return new ProductDto(
                saved.getId(),
                saved.getAccountNumber(),
                saved.getBalance(),
                saved.getProductType(),
                saved.getUser().getId()
        );
    }

    public List<ProductDto> getAllProductsByUserId(Long userId) {
        // Проверяем, существует ли пользователь
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        List<ProductEntity> entities = productRepository.findByUserId(userId);
        return entities.stream()
                .map(entity -> new ProductDto(
                        entity.getId(),
                        entity.getAccountNumber(),
                        entity.getBalance(),
                        entity.getProductType(),
                        entity.getUser().getId()
                ))
                .collect(Collectors.toList());
    }

    public Optional<ProductDto> getProductById(Long productId) {
        return productRepository.findByIdWithUser(productId)
                .map(entity -> new ProductDto(
                        entity.getId(),
                        entity.getAccountNumber(),
                        entity.getBalance(),
                        entity.getProductType(),
                        entity.getUser().getId()
                ));
    }

    public List<ProductDto> getAllProducts() {
        return productRepository.findAllWithUser().stream()
                .map(entity -> new ProductDto(
                        entity.getId(),
                        entity.getAccountNumber(),
                        entity.getBalance(),
                        entity.getProductType(),
                        entity.getUser().getId()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}