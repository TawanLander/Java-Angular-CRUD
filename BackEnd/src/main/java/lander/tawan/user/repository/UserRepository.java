package lander.tawan.user.repository;

import lander.tawan.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    List<UserEntity> findAllByDeletedAtIsNull();
    List<UserEntity> findAllByDeletedAtIsNotNull();
    Optional<UserEntity> findByIdAndDeletedAtIsNull(UUID id);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    boolean existsByEmailAndIdNot(String email, UUID id);
    boolean existsByPhoneAndIdNot(String phone, UUID id);
}