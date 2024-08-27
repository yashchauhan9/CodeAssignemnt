package com.demo.user.management.service.impl;

import com.demo.user.management.dto.UserDto;
import com.demo.user.management.entity.Role;
import com.demo.user.management.entity.User;
import com.demo.user.management.entity.UserStatus;
import com.demo.user.management.mapper.UserMapper;
import com.demo.user.management.mapper.UserMapperImpl;
import com.demo.user.management.repo.UserRepository;
import com.demo.user.management.service.AuditService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    private final UserMapper userMapper = new UserMapperImpl();

    @Mock
    private AuditService auditService;

    private UserServiceImpl userService;

    private User mockUser;
    private UserDto mockUserDto;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository, userMapper, auditService);
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("admin");
        mockUser.setStatus(UserStatus.APPROVED);
        mockUser.setRoles(List.of(Role.ADMIN));

        mockUserDto = new UserDto();
        mockUserDto.setUsername("admin");
    }

    private void mockAuth() {
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication auth = mock(Authentication.class);

        when(securityContext.getAuthentication()).thenReturn(auth);
        when(auth.getPrincipal()).thenReturn(mockUser);
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(getDbUsers());

        List<UserDto> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("u1", result.get(0).getUsername());
        assertEquals("u2", result.get(1).getUsername());

        verify(userRepository, times(1)).findAll();
    }

    //@Test FIXME: mockito-inline issue
    void getUser() {
        mockAuth();
        UserDto result = userService.getUser();
        assertNotNull(result);
    }

    @Test
    void getUsersWithStatus() {
        when(userRepository.findByStatus(UserStatus.PENDING)).thenReturn(getDbUsers());
        var result = userService.getUsersWithStatus(UserStatus.PENDING);
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    private List<User> getDbUsers() {
        User u1 = new User();
        u1.setUsername("u1");

        User u2 = new User();
        u2.setUsername("u2");
        return List.of(u1, u2);
    }


}
