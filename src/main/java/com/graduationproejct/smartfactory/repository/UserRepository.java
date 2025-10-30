package com.graduationproejct.smartfactory.repository;

import com.graduationproejct.smartfactory.domain.entity.User;
import com.graduationproejct.smartfactory.domain.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findFirstByEmail(String email);
    Optional<User> findByUserRole(UserRole userRoleEnum);
}
