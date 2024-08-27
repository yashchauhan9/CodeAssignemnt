package com.demo.user.management.repo;

import com.demo.user.management.entity.Role;
import com.demo.user.management.entity.User;
import com.demo.user.management.entity.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    List<User> findByRoles(Role role);
    List<User> findByStatus(UserStatus status);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
