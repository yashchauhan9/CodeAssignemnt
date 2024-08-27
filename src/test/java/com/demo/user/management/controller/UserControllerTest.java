package com.demo.user.management.controller;

import com.demo.user.management.dto.UpdateUserRequest;
import com.demo.user.management.dto.UserDto;
import com.demo.user.management.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    void init() {
        userController = new UserController(userService);
    }

    @Test
    void getUser() {
        var res = new UserDto();
        when(userService.getUser()).thenReturn(res);
        var response = userController.getUser();
        assertEquals(res, response);
        verify(userService).getUser();
    }

    @Test
    void updateUser() {
        var req = new UpdateUserRequest();
        userController.updateUser(req);
        verify(userService).updateByUser(req);
    }
}