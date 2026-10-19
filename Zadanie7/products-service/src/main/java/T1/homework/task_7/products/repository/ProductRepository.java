package T1.homework.task_7.products.repository;

import T1.homework.task_7.products.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query("SELECT p FROM ProductEntity p " +
            "JOIN FETCH p.user " +
            "WHERE p.user.id = :userId")
    List<ProductEntity> findByUserId(@Param("userId") Long userId);

    @Query("SELECT p FROM ProductEntity p " +
            "JOIN FETCH p.user " +
            "WHERE p.id = :id")
    Optional<ProductEntity> findByIdWithUser(@Param("id") Long id);

    @Query("SELECT p FROM ProductEntity p " +
            "JOIN FETCH p.user")
    List<ProductEntity> findAllWithUser();
}