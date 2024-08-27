package com.demo.user.management.service.impl;

import com.demo.user.management.dto.RoleRequest;
import com.demo.user.management.dto.UpdateUserRequest;
import com.demo.user.management.dto.UserDto;
import com.demo.user.management.dto.UserNotFoundException;
import com.demo.user.management.entity.Audit;
import com.demo.user.management.entity.AuditType;
import com.demo.user.management.entity.Role;
import com.demo.user.management.entity.User;
import com.demo.user.management.entity.UserStatus;
import com.demo.user.management.mapper.UserMapper;
import com.demo.user.management.repo.UserRepository;
import com.demo.user.management.service.AuditService;
import com.demo.user.management.service.ObjectMapperUtil;
import com.demo.user.management.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

import static com.demo.user.management.entity.Role.VIEW_PORTFOLIO;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final AuditService auditService;

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userMapper.userToUserDTO(userRepository.findAll());
    }

    @Override
    public UserDto getUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userMapper.userToUserDTO(user);
    }

    @Override
    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::userToUserDTO)
                .orElseThrow(() -> new UserNotFoundException("user not found"));
    }

    @Override
    public List<UserDto> getUsersWithStatus(UserStatus status) {
        return userMapper.userToUserDTO(userRepository.findByStatus(status));
    }

    @Override
    public void approveUserStatus(@PathVariable Long id) {
        User adminUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (Objects.equals(adminUser.getId(), id)) {
            throw new IllegalArgumentException("Invalid operation");
        }
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("user not found"));
        String auditFrom = ObjectMapperUtil.toJsonString(user);
        if (user.getStatus() == UserStatus.PENDING) {
            user.setStatus(UserStatus.APPROVED);
            user.setLastModifiedTime(ZonedDateTime.now());
            user.setLastModifiedBy(adminUser.getUsername());
            userRepository.save(user);
            auditService.audit(new Audit(AuditType.UPDATE, user.getId(), auditFrom, ObjectMapperUtil.toJsonString(user)));
            return;
        }
        throw new IllegalArgumentException("User not approved");
    }

    @Override
    public void deleteUser(Long id) {
        User adminUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (Objects.equals(adminUser.getId(), id)) {
            throw new IllegalArgumentException("Invalid operation");
        }
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("user not found"));
        String auditFrom = ObjectMapperUtil.toJsonString(user);
        user.setStatus(UserStatus.DELETED);
        user.setLastModifiedTime(ZonedDateTime.now());
        user.setLastModifiedBy(adminUser.getUsername());
        userRepository.save(user);
        auditService.audit(new Audit(AuditType.DELETE, user.getId(), auditFrom, ObjectMapperUtil.toJsonString(user)));
    }

    @Override
    public UserDto updateByUser(UpdateUserRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String auditFrom = ObjectMapperUtil.toJsonString(user);
        if (user.getStatus() == UserStatus.APPROVED) {
            user.setName(request.getName());
            user.setDetails(request.getDetails());
            user.setLastModifiedTime(ZonedDateTime.now());
            user.setLastModifiedBy(user.getUsername());
            User savedUser = userRepository.save(user);
            auditService.audit(new Audit(AuditType.UPDATE, user.getId(), auditFrom, ObjectMapperUtil.toJsonString(user)));
            return userMapper.userToUserDTO(savedUser);
        }
        throw new IllegalArgumentException("User not approved");
    }

    @Override
    public UserDto assignRole(RoleRequest roleRequest) {
        User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (roleRequest.getRole() != VIEW_PORTFOLIO) {
            throw new IllegalArgumentException("Cannot assign role: "+ roleRequest.getRole());
        }

        User user = userRepository.findById(roleRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException("user not found"));
        String auditFrom = ObjectMapperUtil.toJsonString(user);
        if (user.getStatus() == UserStatus.APPROVED) {
            List<Role> roles = user.getRoles();
            roles.add(VIEW_PORTFOLIO);
            user.setRoles(roles);
            user.setLastModifiedTime(ZonedDateTime.now());
            user.setLastModifiedBy(loggedUser.getUsername());
            user.setDetails(user.getDetails() + " " + roleRequest.getDetails());
            User savedUser = userRepository.save(user);
            auditService.audit(new Audit(AuditType.ASSIGN_ROLE, user.getId(), auditFrom, ObjectMapperUtil.toJsonString(user)));
            return userMapper.userToUserDTO(savedUser);
        }
        log.warn("User not approved for assignRole");
        throw new IllegalArgumentException("User not approved");
    }


}
