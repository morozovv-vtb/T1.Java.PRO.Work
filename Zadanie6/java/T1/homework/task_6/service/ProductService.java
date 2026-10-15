package T1.homework.task_6.service;

import T1.homework.task_6.dto.ProductDto;
import T1.homework.task_6.exception.UserNotFoundException;
import T1.homework.task_6.mapper.ProductMapper;
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
    private final ProductMapper productMapper;

    // Конструкторная инъекция
    public ProductService(ProductRepository productRepository,
                          UserRepository userRepository,
                          ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.productMapper = productMapper;
    }

    @Transactional
    public ProductDto createProduct(String accountNumber, BigDecimal balance, String productType, Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        // Используем маппер для создания Entity
        ProductEntity product = new ProductEntity(accountNumber, balance, productType, user);
        ProductEntity saved = productRepository.save(product);

        // Преобразуем Entity в DTO через маппер
        return productMapper.toDto(saved);
    }

    public List<ProductDto> getAllProductsByUserId(Long userId) {
        // Проверяем, существует ли пользователь
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        List<ProductEntity> entities = productRepository.findByUserId(userId);
        // Преобразуем список через маппер
        return productMapper.toDtoList(entities);
    }

    public Optional<ProductDto> getProductById(Long productId) {
        return productRepository.findByIdWithUser(productId)
                .map(productMapper::toDto); // Лямбда-ссылка на метод маппера
    }

    public List<ProductDto> getAllProducts() {
        return productMapper.toDtoList(productRepository.findAllWithUser()); // ✅ Всё в одной строке
    }

    @Transactional
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}