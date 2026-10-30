package T1.homework.task_7.products.service;

import T1.homework.task_7.products.model.UserEntity;
import T1.homework.task_7.common.dto.ProductDto;
import T1.homework.task_7.products.exception.ProductNotFoundException;
import T1.homework.task_7.products.exception.UserNotFoundException;
import T1.homework.task_7.products.mapper.ProductMapper;
import T1.homework.task_7.products.model.ProductEntity;
import T1.homework.task_7.products.repository.ProductRepository;
import T1.homework.task_7.products.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public ProductDto createProduct(ProductDto productDto) {
        Long userId = productDto.getUserId();
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        // Используем маппер для преобразования DTO → Entity
        ProductEntity product = productMapper.toEntity(productDto);
        product.setUser(user);

        ProductEntity saved = productRepository.save(product);
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

    public ProductDto getProductById(Long productId) {
        return productRepository.findByIdWithUser(productId)
                .map(productMapper::toDto)
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

    public List<ProductDto> getAllProducts() {
        return productMapper.toDtoList(productRepository.findAllWithUser());
    }

    @Transactional
    public void deleteProduct(Long productId) {
        // Проверка существования — теперь в сервисе!
        productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        productRepository.deleteById(productId);
    }
}