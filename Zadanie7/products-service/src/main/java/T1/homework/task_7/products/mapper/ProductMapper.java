package T1.homework.task_7.products.mapper;

import T1.homework.task_7.common.dto.ProductDto;
import T1.homework.task_7.products.model.ProductEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    // Entity → DTO
    public ProductDto toDto(ProductEntity entity) {
        if (entity == null) return null;
        return new ProductDto(
                entity.getId(),
                entity.getAccountNumber(),
                entity.getBalance(),
                entity.getProductType(),
                entity.getUser() != null ? entity.getUser().getId() : null
        );
    }

    // List<Entity> → List<Dto>
    public List<ProductDto> toDtoList(List<ProductEntity> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // DTO → Entity (для создания)
    public ProductEntity toEntity(ProductDto dto) {
        ProductEntity entity = new ProductEntity();
        entity.setAccountNumber(dto.getAccountNumber());
        entity.setBalance(dto.getBalance());
        entity.setProductType(dto.getProductType());
        return entity;
    }
}