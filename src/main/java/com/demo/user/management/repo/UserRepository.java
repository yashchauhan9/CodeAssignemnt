package com.demo.user.management.repo;

import com.demo.user.management.entity.Role;
import com.demo.user.management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByRole(Role role);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
