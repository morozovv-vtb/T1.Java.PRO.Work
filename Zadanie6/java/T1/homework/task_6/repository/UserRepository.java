package T1.homework.task_6.repository;

import T1.homework.task_6.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Modifying
    @Query(value = "TRUNCATE TABLE users RESTART IDENTITY CASCADE", nativeQuery = true)
    void truncateUsers();
}