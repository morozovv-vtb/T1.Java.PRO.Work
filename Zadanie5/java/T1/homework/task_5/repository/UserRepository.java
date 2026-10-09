package T1.homework.task_5.repository;

import T1.homework.task_5.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Modifying
    @Query(value = "ALTER SEQUENCE users_id_seq RESTART WITH 1", nativeQuery = true)
    @Transactional
    void resetSequence();
}